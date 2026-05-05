package com.cards4play.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Compra {

    private String id;
    private String idCliente;
    private LocalDateTime fecha;
    private List<String> idProductos = new ArrayList<>();
    private double totalCompra;
    private EstadoCompra estado;




}
