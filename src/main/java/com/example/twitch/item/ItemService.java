package com.example.twitch.item;

import com.example.twitch.external.TwitchService;
import com.example.twitch.external.model.Video;
import com.example.twitch.external.model.Clip;
import com.example.twitch.external.model.Stream;
import com.example.twitch.model.TypeGroupedItemList;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // this annotation is used for dependency injection , aim to achieve  decoupling
public class ItemService {

    private static final int SEARCH_RESULT_SIZE = 20;

    private final TwitchService twitchService;

    public ItemService(TwitchService twitchService) {
        this.twitchService = twitchService;
    }

    // get all three types of info  from twitchService and return them to controller
    @Cacheable("items")
    public TypeGroupedItemList getItems(String gameId) {
        List<Video> videos = twitchService.getVideos(gameId, SEARCH_RESULT_SIZE);
        List<Clip> clips = twitchService.getClips(gameId, SEARCH_RESULT_SIZE);
        List<Stream> streams = twitchService.getStreams(List.of(gameId), SEARCH_RESULT_SIZE);
        return new TypeGroupedItemList(gameId, streams, videos, clips);
    }

}