package com.cards4play.models;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Carta.class, name = "CARTA"),
        @JsonSubTypes.Type(value = Booster.class, name = "BOOSTER"),
        @JsonSubTypes.Type(value = ProductoSellado.class, name = "PRODUCTO_SELLADO"),
        @JsonSubTypes.Type(value = Accesorio.class, name = "ACCESORIO")
})
public abstract class Producto {

    protected String id;
    protected String nombre;
    protected double precio;
    protected int stock;
    protected TipoProducto tipoProducto;

}
