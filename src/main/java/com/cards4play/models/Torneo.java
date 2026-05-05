package com.cards4play.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Torneo {

    private String id;
    private String nombre;
    private LocalDate fecha;
    private int capacidadMaxima;
    private EstadoTorneo estado;
    private List<String> idParticipantes = new ArrayList<>();

}
