package com.cards4play.services;

import com.cards4play.models.Booster;
import com.cards4play.models.Carta;
import com.cards4play.models.Producto;
import com.cards4play.models.TipoProducto;
import com.cards4play.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoosterService {

    private final ProductoRepository productoRepo;

    public List<Carta> abrirBooster(String boosterId) {
        Booster booster = (Booster) productoRepo.findById(boosterId)
                .orElseThrow(() -> new IllegalArgumentException("Booster no encontrado"));

        if (booster.isAbierto()) {
            throw new IllegalStateException("Este booster ya fue abierto");
        }

        List<Carta> todasLasCartas = productoRepo.findAll().stream()
                .filter(p -> p.getTipoProducto() == TipoProducto.CARTA)
                .map(p -> (Carta) p)
                .collect(Collectors.toList());

        if (todasLasCartas.isEmpty()) {
            throw new IllegalStateException("No hay cartas registradas en el sistema para generar el booster.");
        }

        Random random = new Random();
        List<Carta> cartasObtenidas = new ArrayList<>();
        List<String> idsCartasObtenidas = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Carta cartaAleatoria = todasLasCartas.get(random.nextInt(todasLasCartas.size()));
            cartasObtenidas.add(cartaAleatoria);
            idsCartasObtenidas.add(cartaAleatoria.getId());
        }

        booster.setIdCartasContenidas(idsCartasObtenidas);
        booster.setAbierto(true);
        productoRepo.save(booster);

        return cartasObtenidas;
    }
}