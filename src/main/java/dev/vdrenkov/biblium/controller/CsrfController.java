package dev.vdrenkov.biblium.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * CsrfController component.
 */
@RestController
public class CsrfController {
    /**
     * Handles csrf operation.
     *
     * @param csrfToken
     *     Generated CSRF token for the current client.
     * @return Response entity containing the requested data.
     */
    @GetMapping("/csrf")
    public ResponseEntity<Map<String, String>> csrf(final CsrfToken csrfToken) {
        return ResponseEntity.ok(Map.of("token", csrfToken.getToken()));
    }
}
