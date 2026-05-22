package com.cards4play.controllers;

import com.cards4play.models.Carta;
import com.cards4play.services.BoosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boosters")
@RequiredArgsConstructor
public class BoosterController {

    private final BoosterService boosterService;

    @PostMapping("/{id}/abrir")
    public ResponseEntity<?> abrir(@PathVariable String id) {
        try {
            List<Carta> cartas = boosterService.abrirBooster(id);
            return ResponseEntity.ok(cartas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}