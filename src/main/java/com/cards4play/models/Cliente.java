package com.cards4play.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Usuario{

    private List<String> idCompras = new ArrayList<>();
    private List<String> idTorneosInscritos = new ArrayList<>();

    public Cliente(){
        this.rol = RolUsuario.CLIENTE;
    }

}
