package com.cards4play.services;

import com.cards4play.models.Cliente;
import com.cards4play.models.Compra;
import com.cards4play.models.EstadoCompra;
import com.cards4play.models.Producto;
import com.cards4play.repositories.CompraRepository;
import com.cards4play.repositories.ProductoRepository;
import com.cards4play.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepo;
    private final ProductoRepository productoRepo;
    private final UsuarioRepository usuarioRepo;

    public Compra realizarCompra(String clienteId, List<String> idsProductosAComprar) {
        Cliente cliente = (Cliente) usuarioRepo.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        double total = 0.0;


        for (String idProd : idsProductosAComprar) {
            Producto p = productoRepo.findById(idProd)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no existe: " + idProd));
            if (p.getStock() <= 0) {
                throw new IllegalStateException("No hay stock para el producto: " + p.getNombre());
            }
            p.setStock(p.getStock() - 1);
            productoRepo.save(p);
            total += p.getPrecio();
        }


        Compra nuevaCompra = new Compra();
        nuevaCompra.setId(UUID.randomUUID().toString()); // Genera ID único
        nuevaCompra.setIdCliente(clienteId);
        nuevaCompra.setFecha(LocalDateTime.now());
        nuevaCompra.setIdProductos(idsProductosAComprar);
        nuevaCompra.setTotalCompra(total);
        nuevaCompra.setEstado(EstadoCompra.COMPLETADA);

        compraRepo.save(nuevaCompra);


        cliente.getIdCompras().add(nuevaCompra.getId());
        usuarioRepo.save(cliente);

        return nuevaCompra;
    }
}