package pl.yndtask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health-check")
@RequiredArgsConstructor
public class HealthCheckController {

    private final HealthEndpoint healthEndpoint;

    @GetMapping("/ping")
    public String ping() {
        return "OK";
    }

    @GetMapping
    public HealthComponent check() {
        return healthEndpoint.health();
    }
}
