package com.piseth.api.auth.web;

public record AuthDto(String bearer, String accessToken) {
}