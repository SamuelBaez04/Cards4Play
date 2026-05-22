package com.cards4play.repositories;

import com.cards4play.models.Torneo;
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
public class TorneoRepository {
    private final String FILE_PATH = "torneos.json";
    private final ObjectMapper mapper;

    public TorneoRepository() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    public List<Torneo> findAll() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try {
            return mapper.readValue(file, new TypeReference<List<Torneo>>() {});
        } catch (IOException e) {
            System.err.println("Error al leer torneos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAll(List<Torneo> torneos) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), torneos);
        } catch (IOException e) {
            System.err.println("Error al guardar torneos: " + e.getMessage());
        }
    }

    public Optional<Torneo> findById(String id) {
        return findAll().stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public void save(Torneo torneo) {
        List<Torneo> torneos = findAll();
        torneos.removeIf(t -> t.getId().equals(torneo.getId()));
        torneos.add(torneo);
        saveAll(torneos);
    }
}