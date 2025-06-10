package com.perfulandia.inventario.service;

import com.perfulandia.inventario.dto.InventarioDTO;
import com.perfulandia.inventario.model.Inventario;
import com.perfulandia.inventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    private InventarioDTO toDTO(Inventario inventario) {
        return new InventarioDTO(
                inventario.getId(),
                inventario.getIdProducto(),
                inventario.getStockDisponible(),
                inventario.getUbicacionBodega()
        );
    }

    private Inventario toEntity(InventarioDTO dto) {
        return new Inventario(
                dto.getIdInventario(),
                dto.getIdProducto(),
                dto.getStockDisponible(),
                dto.getUbicacionBodega()
        );
    }

    public InventarioDTO crear(InventarioDTO dto) {
        Inventario inventario = toEntity(dto);
        return toDTO(inventarioRepository.save(inventario));
    }

    public List<InventarioDTO> listar() {
    return inventarioRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
}

    public InventarioDTO obtenerPorId(Integer id) {
        Optional<Inventario> inventario = inventarioRepository.findById(id);
        return inventario.map(this::toDTO).orElse(null); // puedes lanzar excepción si prefieres
    }

    public InventarioDTO actualizar(Integer id, InventarioDTO dto) {
        Optional<Inventario> existenteOpt = inventarioRepository.findById(id);
        if (existenteOpt.isEmpty()) {
            return null;
        }

        Inventario existente = existenteOpt.get();
        existente.setIdProducto(dto.getIdProducto());
        existente.setStockDisponible(dto.getStockDisponible());
        existente.setUbicacionBodega(dto.getUbicacionBodega());

        return toDTO(inventarioRepository.save(existente));
    }

    public boolean eliminar(Integer id) {
        Optional<Inventario> inventario = inventarioRepository.findById(id);
        if (inventario.isEmpty()) {
            return false;
        }
        inventarioRepository.deleteById(id);
        return true;
    }
}
