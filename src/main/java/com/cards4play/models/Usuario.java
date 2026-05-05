package com.cards4play.models;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoUsuario")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Administrador.class, name = "ADMIN"),
        @JsonSubTypes.Type(value = Cliente.class, name = "ClIENTE")
})
public abstract class Usuario {

    protected String id;
    protected String nombre;
    protected String email;
    protected String passwordHash;
    protected RolUsuario rol;



}
