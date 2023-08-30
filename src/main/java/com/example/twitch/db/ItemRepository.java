package com.example.twitch.db;

import com.example.twitch.db.entity.ItemEntity;
import org.springframework.data.repository.ListCrudRepository;

// used for query and db manipulation
public interface ItemRepository extends ListCrudRepository<ItemEntity, Long> {

    ItemEntity findByTwitchId(String twitchId);// function name is crucial, serves as query



}