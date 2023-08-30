package com.example.twitch.favorite;

import com.example.twitch.db.FavoriteRecordRepository;
import com.example.twitch.db.ItemRepository;
import com.example.twitch.db.entity.FavoriteRecordEntity;
import com.example.twitch.db.entity.ItemEntity;
import com.example.twitch.db.entity.UserEntity;
import com.example.twitch.model.TypeGroupedItemList;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class FavoriteService {
    private final ItemRepository itemRepository;
    private final FavoriteRecordRepository favoriteRecordRepository;

    //dependency injection
    public FavoriteService(ItemRepository itemRepository,
                           FavoriteRecordRepository favoriteRecordRepository) {
        this.itemRepository = itemRepository;
        this.favoriteRecordRepository = favoriteRecordRepository;
    }

    // key is UserEntity user, since actions are related to user
    @CacheEvict(cacheNames = "recommend_items", key = "#root.args[0]") // when set/unset fovorite items, old cache expires, all cache is evicted
    @Transactional // make sure all actions in the function  are completed all together, if any failure happens roll back ,revert back to original state
    public void setFavoriteItem(UserEntity user, ItemEntity item) throws DuplicateFavoriteException {
        ItemEntity persistedItem = itemRepository.findByTwitchId(item.twitchId());// check if the item  exits in item db
        if (persistedItem == null) {
            persistedItem = itemRepository.save(item);// assign item to persistedItem if not exits
        }
        if (favoriteRecordRepository.existsByUserIdAndItemId(user.id(), persistedItem.id())) {
            // if the favorite record has been set previously by the user
            throw new DuplicateFavoriteException();
        }

        // create the favorite record and save the favorite record into favorite db
        FavoriteRecordEntity favoriteRecord = new FavoriteRecordEntity(null, user.id(), persistedItem.id(), Instant.now());
        favoriteRecordRepository.save(favoriteRecord);
    }

    @CacheEvict(cacheNames = "recommend_items", key = "#root.args[0]")
    public void unsetFavoriteItem(UserEntity user, String twitchId) {
        ItemEntity item = itemRepository.findByTwitchId(twitchId);
        if (item != null) {// only delete from favorite db  if the item exits in the item db
            favoriteRecordRepository.delete(user.id(), item.id());
        }
    }

    public List<ItemEntity> getFavoriteItems(UserEntity user) {
        List<Long> favoriteItemIds = favoriteRecordRepository.findFavoriteItemIdsByUserId(user.id());
        return itemRepository.findAllById(favoriteItemIds);
    }


    // separate item list into three types : clip, stream and video
    public TypeGroupedItemList getGroupedFavoriteItems(UserEntity user) {
        List<ItemEntity> items = getFavoriteItems(user);
        return new TypeGroupedItemList(items);
    }
}
