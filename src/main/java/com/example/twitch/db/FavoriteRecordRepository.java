package com.example.twitch.db;

import com.example.twitch.db.entity.FavoriteRecordEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface FavoriteRecordRepository extends ListCrudRepository<FavoriteRecordEntity,Long> {
    List<FavoriteRecordEntity> findAllByUserId(Long userId);
    boolean existsByUserIdAndItemId(Long userId, Long itemId);

    // more complex query needed to be specified in sql
    @Query("select item_id from favorite_records where user_id= :userId") // since item_id is long , fn return
    // type is List<Long>
    List<Long> findFavoriteItemIdsByUserId(Long userId);

    @Modifying // need "modifying" when deleting, inserting, updating
    @Query("delete from favorite_records where user_id = :userId and item_id = :itemId")
    void delete(Long userId, Long itemId);

}
