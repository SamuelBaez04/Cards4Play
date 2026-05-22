package com.cards4play.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Accesorio extends Producto{

    private String categoria;
    private String descripcion;

    public Accesorio(){
        this.tipoProducto = TipoProducto.ACCESORIO;
    }
}
