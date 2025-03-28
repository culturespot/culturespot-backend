package com.culturespot.culturespotdomain.common;

import lombok.Getter;

public interface DiscordMessageService {

  void send(String title, String description, Color color);

  enum Color {
    INFO(0x21C6BD),
    WARN(0xFF0101),
    ERROR(0xFF0101);

    @Getter
    private final int hex;

    Color(int hex) {
      this.hex = hex;
    }
  }
}
