package com.perfulandia.inventario.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO {
    private Integer idInventario;
    private Integer idProducto;
    private Integer stockDisponible;
    private String ubicacionBodega;
}
