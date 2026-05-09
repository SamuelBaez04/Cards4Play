package com.cards4play.services;

import com.cards4play.models.Carta;
import com.cards4play.models.Producto;
import com.cards4play.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepo;
    private final CurrencyService currencyService;

    public Producto registrarProducto(Producto producto) {
        producto.setId(UUID.randomUUID().toString());
        productoRepo.save(producto);
        return producto;
    }

    public void eliminarProducto(String id) {
        productoRepo.deleteById(id);
    }

    public List<Producto> consultarInventario() {
        List<Producto> inventario = productoRepo.findAll();

        double tasaActualCOP = currencyService.obtenerTasaCambioUsdToCop();

        for (Producto p : inventario) {
            if (p instanceof Carta) {
                Carta c = (Carta) p;
                c.setPrecioCOP( c.getPrecioUSD() * tasaActualCOP);
            }
        }

        return inventario;
    }

    public Producto buscarPorId(String id) {
        return productoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    }
}