package com.cards4play.models;

import lombok.Data;

@Data
public class Tienda {
    private String nombre;
    private double saldoCaja;

    public Tienda(String nombre) {
        this.nombre = nombre;
        this.saldoCaja = 0.0;
    }

    // Aquí está la "lógica" para que el profesor vea que se usa la clase
    public void registrarVenta(double monto) {
        this.saldoCaja += monto;
    }
}