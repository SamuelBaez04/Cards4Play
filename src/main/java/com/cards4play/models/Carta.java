package com.cards4play.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Carta extends Producto{

    private String rareza;

    public Carta(){
        this.tipoProducto = TipoProducto.CARTA;
    }


}
