package com.perfulandia.inventario.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Integer id;

    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "stock_disponible")
    private Integer stockDisponible;

    @Column(name = "ubicacion_bodega")
    private String ubicacionBodega;
}
