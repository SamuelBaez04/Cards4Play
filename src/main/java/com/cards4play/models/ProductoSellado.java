package com.cards4play.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductoSellado extends Producto{

    private String descripcion;

    public ProductoSellado(){
        this.tipoProducto = TipoProducto.PRODUCTO_SELLADO;
    }
}
