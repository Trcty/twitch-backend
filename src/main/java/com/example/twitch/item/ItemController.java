package com.example.twitch.item;

import com.example.twitch.model.TypeGroupedItemList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/search")// get everything (video, clip, stream)related to input gameID
    // controller serves as a transitional rule,(receive request), service actually does the heavy work(perform main logic)
    public TypeGroupedItemList search(@RequestParam("game_id") String gameId) {
        return itemService.getItems(gameId);
    }
}
