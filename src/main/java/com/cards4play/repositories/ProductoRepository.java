package com.cards4play.repositories;

import com.cards4play.models.Producto;
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
public class ProductoRepository {
    private final String FILE_PATH = "productos.json";
    private final ObjectMapper mapper;

    public ProductoRepository() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    public List<Producto> findAll() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try {
            return mapper.readValue(file, new TypeReference<List<Producto>>() {});
        } catch (IOException e) {
            System.err.println("Error al leer productos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAll(List<Producto> productos) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), productos);
        } catch (IOException e) {
            System.err.println("Error al guardar productos: " + e.getMessage());
        }
    }

    public Optional<Producto> findById(String id) {
        return findAll().stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public void save(Producto producto) {
        List<Producto> productos = findAll();
        productos.removeIf(p -> p.getId().equals(producto.getId()));
        productos.add(producto);
        saveAll(productos);
    }

    public void deleteById(String id) {
        List<Producto> productos = findAll();
        productos.removeIf(p -> p.getId().equals(id));
        saveAll(productos);
    }
}