package com.example.twitch.model;


// customize error message
public record TwitchErrorResponse(
        String message,
        String error,
        String details
) {
}
