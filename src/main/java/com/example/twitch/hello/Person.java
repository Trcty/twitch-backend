package com.example.twitch.hello;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Person(
        String name,
        String company,
        @JsonProperty("home_address") Address homeAddress, //chang class name into snake_case
         @JsonProperty("favorite_book") Book favoriteBook

) {
}
