package com.cards4play.services;

import com.cards4play.models.Administrador;
import com.cards4play.models.Cliente;
import com.cards4play.models.Usuario;
import com.cards4play.repositories.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;

    @PostConstruct
    public void inicializarAdminPorDefecto() {
        Optional<Usuario> adminExistente = usuarioRepo.findByEmail("admin@cards4play.com");
        if (adminExistente.isEmpty()) {
            Administrador admin = new Administrador();
            admin.setId(UUID.randomUUID().toString());
            admin.setNombre("Super Admin");
            admin.setEmail("admin@cards4play.com");
            admin.setPasswordHash("admin123");
            usuarioRepo.save(admin);
            System.out.println("Administrador por defecto creado exitosamente.");
        }
    }

    public Usuario autenticar(String email, String password) {
        return usuarioRepo.findByEmail(email)
                .filter(u -> u.getPasswordHash().equals(password))
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));
    }

    public Cliente registrarCliente(Cliente nuevoCliente) {
        if (usuarioRepo.findByEmail(nuevoCliente.getEmail()).isPresent()) {
            throw new IllegalStateException("El correo ya está registrado");
        }
        nuevoCliente.setId(UUID.randomUUID().toString());
        usuarioRepo.save(nuevoCliente);
        return nuevoCliente;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepo.findAll();
    }
}