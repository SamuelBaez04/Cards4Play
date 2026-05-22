package com.cards4play.controllers;

import com.cards4play.models.Cliente;
import com.cards4play.models.RolUsuario;
import com.cards4play.models.Usuario;
import com.cards4play.security.JwtService;
import com.cards4play.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService; // Inyectamos el servicio de tokens

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            Usuario usuario = usuarioService.autenticar(credentials.get("email"), credentials.get("password"));

            // Si pasa de aquí, la autenticación fue un éxito. El problema es el JWT.
            String token = jwtService.generarToken(usuario);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // Este es el error real de credenciales malas
            return ResponseEntity.status(401).body(e.getMessage());

        } catch (Exception e) {
            // Este es el error oculto de JJWT
            System.out.println("💥 ERROR CRÍTICO AL GENERAR TOKEN JWT:");
            e.printStackTrace(); // Esto imprimirá toda la traza roja en tu consola
            return ResponseEntity.status(500).body("Error interno generando JWT: " + e.getMessage());
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok(usuarioService.registrarCliente(cliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}