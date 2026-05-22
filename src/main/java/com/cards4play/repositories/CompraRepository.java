package com.cards4play.repositories;

import com.cards4play.models.Compra;
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
public class CompraRepository {
    private final String FILE_PATH = "compras.json";
    private final ObjectMapper mapper;

    public CompraRepository() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    public List<Compra> findAll() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try {
            return mapper.readValue(file, new TypeReference<List<Compra>>() {});
        } catch (IOException e) {
            System.err.println("Error al leer compras: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAll(List<Compra> compras) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), compras);
        } catch (IOException e) {
            System.err.println("Error al guardar compras: " + e.getMessage());
        }
    }

    public Optional<Compra> findById(String id) {
        return findAll().stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public List<Compra> findByIdCliente(String idCliente) {
        return findAll().stream()
                .filter(c -> c.getIdCliente().equals(idCliente))
                .toList();
    }

    public void save(Compra compra) {
        List<Compra> compras = findAll();
        compras.removeIf(c -> c.getId().equals(compra.getId()));
        compras.add(compra);
        saveAll(compras);
    }
}