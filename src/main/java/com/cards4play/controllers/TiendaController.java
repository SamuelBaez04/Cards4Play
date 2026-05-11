package com.cards4play.controllers;

import com.cards4play.services.TiendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tienda")
public class TiendaController {

    private final TiendaService tiendaService;

    public TiendaController(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    @PostMapping("/comprar/{idProducto}")
    public ResponseEntity<?> realizarCompra(@PathVariable String idProducto, Authentication auth) {
        try {
            // Sacamos el correo del que está logueado directamente del Token
            String email = auth.getName();
            String resultado = tiendaService.facturarCompraPlana(email, idProducto);
            return ResponseEntity.ok(Map.of("mensaje", resultado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/caja")
    public ResponseEntity<?> verCajaRegistradora() {
        return ResponseEntity.ok(tiendaService.obtenerInfoTienda());
    }
}