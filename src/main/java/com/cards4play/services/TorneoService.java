package com.cards4play.services;

import com.cards4play.models.Cliente;
import com.cards4play.models.EstadoTorneo;
import com.cards4play.models.Torneo;
import com.cards4play.repositories.TorneoRepository;
import com.cards4play.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TorneoService {

    private final TorneoRepository torneoRepo;
    private final UsuarioRepository usuarioRepo;

    public void inscribirClienteEnTorneo(String torneoId, String clienteId) {
        Torneo torneo = torneoRepo.findById(torneoId)
                .orElseThrow(() -> new IllegalArgumentException("Torneo no encontrado"));

        Cliente cliente = (Cliente) usuarioRepo.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));


        if (torneo.getIdParticipantes().size() >= torneo.getCapacidadMaxima()) {
            torneo.setEstado(EstadoTorneo.LLENO);
            torneoRepo.save(torneo);
            throw new IllegalStateException("El torneo ya ha alcanzado su capacidad máxima.");
        }


        if (torneo.getIdParticipantes().contains(clienteId)) {
            throw new IllegalStateException("El cliente ya está inscrito en este torneo.");
        }

        torneo.getIdParticipantes().add(cliente.getId());
        cliente.getIdTorneosInscritos().add(torneo.getId());


        if (torneo.getIdParticipantes().size() == torneo.getCapacidadMaxima()) {
            torneo.setEstado(EstadoTorneo.LLENO);
        }

        torneoRepo.save(torneo);
        usuarioRepo.save(cliente);
    }
}