package com.cards4play.repositories;

import com.cards4play.models.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {
    private final String FILE_PATH = "usuarios.json";
    private final ObjectMapper mapper;

    public UsuarioRepository() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    public List<Usuario> findAll() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try {
            return mapper.readValue(file, new TypeReference<List<Usuario>>() {});
        } catch (IOException e) {
            System.err.println("Error al leer usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAll(List<Usuario> usuarios) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), usuarios);
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    public Optional<Usuario> findById(String id) {
        return findAll().stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Optional<Usuario> findByEmail(String email) {
        return findAll().stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }

    public void save(Usuario usuario) {
        List<Usuario> usuarios = findAll();
        usuarios.removeIf(u -> u.getId().equals(usuario.getId()));
        usuarios.add(usuario);
        saveAll(usuarios);
    }
}