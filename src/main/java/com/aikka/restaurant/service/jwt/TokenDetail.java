package com.aikka.restaurant.service.jwt;

public record TokenDetail(Integer userId, String username) {

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
