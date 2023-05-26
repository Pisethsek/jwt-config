package com.piseth.api.user.web;

public record UserPostDto(String username,
                          String profile,
                          String email,
                          String password) {
}
