package com.cards4play.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Administrador extends Usuario{

    public Administrador(){
        this.rol = RolUsuario.ADMIN;
    }

}
