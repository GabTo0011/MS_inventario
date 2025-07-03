package com.perfulandia.inventario.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO extends RepresentationModel<InventarioDTO> {
    private Integer idInventario;
    private Integer idProducto;
    private Integer stockDisponible;
    private String ubicacionBodega;
}
