package com.piseth.api.book.web;

import jakarta.validation.constraints.NotBlank;

public record BookDto(@NotBlank(message = "title cannot blank") String title,
                      Integer image,
                      String description) {
}
