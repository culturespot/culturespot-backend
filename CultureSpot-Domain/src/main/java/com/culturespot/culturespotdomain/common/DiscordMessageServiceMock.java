package com.culturespot.culturespotdomain.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Profile(value = {"local", "dev"})
@Service
public class DiscordMessageServiceMock implements DiscordMessageService {

  @Override
  public void send(String title, String description, Color color) {
    log.info("title: {}, description: {}, color: {}", title, description, color);
    log.info("Completed to send Discord message.");
  }
}
