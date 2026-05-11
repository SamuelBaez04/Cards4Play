package com.cards4play.services;

import com.cards4play.models.Producto;
import com.cards4play.models.Tienda;
import com.cards4play.repositories.ProductoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TiendaService {

    private Tienda miTienda;
    private final ProductoRepository productoRepo;

    public TiendaService(ProductoRepository productoRepo) {
        this.productoRepo = productoRepo;
    }

    @PostConstruct
    public void init() {
        this.miTienda = new Tienda("Cards4Play Oficial");
    }

    public String facturarCompraPlana(String emailCliente, String idProducto) {
        Optional<Producto> productoOpt = productoRepo.findAll().stream()
                .filter(p -> p.getId().equals(idProducto))
                .findFirst();

        if (productoOpt.isEmpty()) {
            throw new IllegalArgumentException("El producto no existe en el catálogo.");
        }

        Producto producto = productoOpt.get();

        // Le sumamos la plata a nuestra clase Tienda
        miTienda.registrarVenta(producto.getPrecioUSD());

        // Devolvemos el recibo
        return "Factura: El usuario " + emailCliente + " compró [" + producto.getNombre() + "]. " +
                "La tienda ahora tiene un saldo total de: $" + miTienda.getSaldoCaja() + " USD.";
    }

    public Tienda obtenerInfoTienda() {
        return this.miTienda;
    }
}