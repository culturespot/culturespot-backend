package com.culturespot.culturespotdomain.common;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Profile(value = {"develop", "production"})
@Service
public class DiscordMessageServiceImpl implements DiscordMessageService {

  @Value("${discord.webhook}")
  private String webHook;

  @Override
  public void send(String title, String description, Color color) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, List<Embed>> payload = new HashMap<>();
    payload.put("embeds", Lists.newArrayList(new Embed(title, description, color.getHex())));

    try {
      ResponseEntity<String> response = new RestTemplate().postForEntity(webHook,
          new HttpEntity<>(payload, headers), String.class);

      if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
        log.error(
            "Error occurred while sending message. Response status code: {}, Response body: {}",
            response.getStatusCode(), response.getBody());
      }
    } catch (Exception e) {
      log.error("Failed to send Discord message", e);
    }
  }

  private record Embed(String title, String description, int color) {

  }
}
