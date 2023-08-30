package com.example.twitch.model;

import com.fasterxml.jackson.annotation.JsonProperty;


// collect user info during user registration
public record RegisterBody(
        String username,
        String password,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName
) {
}
