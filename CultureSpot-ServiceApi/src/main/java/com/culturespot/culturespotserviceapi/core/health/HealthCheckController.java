package com.culturespot.culturespotserviceapi.core.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping
    public String healthCheck() {
        return "I'm alive!";
    }

    @GetMapping("/test")
    public String test() {
        return "API is working!";
    }
}
