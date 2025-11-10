package org.example.backend.Controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestHealthController {
    private final HealthController healthController = new HealthController();
    @Test
    public void testHealth() {
        Map<String, String> result = Map.of("status", "OK");

        Map<String, String> health = healthController.health();

        assertEquals(result, health);
    }
}
