package com.cards4play.controllers;

import com.cards4play.services.TorneoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/torneos")
@RequiredArgsConstructor
public class TorneoController {

    private final TorneoService torneoService;

    @PostMapping("/{torneoId}/inscribir/{clienteId}")
    public ResponseEntity<?> inscribir(@PathVariable String torneoId, @PathVariable String clienteId) {
        try {
            torneoService.inscribirClienteEnTorneo(torneoId, clienteId);
            return ResponseEntity.ok("Inscripción exitosa");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}