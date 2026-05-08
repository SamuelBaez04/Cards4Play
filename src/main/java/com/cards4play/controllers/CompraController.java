package com.cards4play.controllers;

import com.cards4play.models.Compra;
import com.cards4play.services.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    public ResponseEntity<?> comprar(@RequestBody Map<String, Object> request) {
        try {
            String clienteId = (String) request.get("clienteId");
            List<String> idsProductos = (List<String>) request.get("idsProductos");
            Compra compra = compraService.realizarCompra(clienteId, idsProductos);
            return ResponseEntity.ok(compra);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}