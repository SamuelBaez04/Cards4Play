package com.cards4play.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Booster extends Producto{

    private List<String> idCartasContenidas = new ArrayList<>();
    private boolean abierto = false;

    public Booster(){
        this.tipoProducto = TipoProducto.BOOSTER;
    }


}
