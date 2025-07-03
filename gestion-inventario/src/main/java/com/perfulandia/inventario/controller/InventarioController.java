package com.perfulandia.inventario.controller;

import com.perfulandia.inventario.dto.InventarioDTO;
import com.perfulandia.inventario.service.InventarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(value = "Inventario API", description = "Operaciones relacionadas con el inventario")
@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService service;

    @ApiOperation(value = "Crear un nuevo inventario", response = InventarioDTO.class)
    @PostMapping
    public ResponseEntity<InventarioDTO> crear(@RequestBody InventarioDTO dto) {
        InventarioDTO creado = service.crear(dto);
        // HATEOAS: Link para obtener el inventario recién creado
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(InventarioController.class).obtener(creado.getIdInventario())).withSelfRel();
        creado.add(selfLink);
        return ResponseEntity.created(URI.create("/api/inventario/" + creado.getIdInventario())).body(creado);
    }

    @ApiOperation(value = "Obtener todos los inventarios", response = List.class)
    @GetMapping
    public ResponseEntity<List<InventarioDTO>> listar() {
        List<InventarioDTO> inventarios = service.listar();
        inventarios.forEach(inventario -> {
            // HATEOAS: Link para cada inventario
            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(InventarioController.class).obtener(inventario.getIdInventario())).withSelfRel();
            inventario.add(selfLink);
        });
        return ResponseEntity.ok(inventarios);
    }

    @ApiOperation(value = "Obtener un inventario por su ID", response = InventarioDTO.class)
    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> obtener(@PathVariable Integer id) {
        InventarioDTO inventario = service.obtenerPorId(id);
        if (inventario == null) {
            return ResponseEntity.notFound().build();
        }
        // HATEOAS: Link para el inventario obtenido
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(InventarioController.class).obtener(id)).withSelfRel();
        inventario.add(selfLink);
        return ResponseEntity.ok(inventario);
    }

    @ApiOperation(value = "Actualizar un inventario por su ID", response = InventarioDTO.class)
    @PutMapping("/{id}")
    public ResponseEntity<InventarioDTO> actualizar(@PathVariable Integer id, @RequestBody InventarioDTO dto) {
        InventarioDTO actualizado = service.actualizar(id, dto);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        // HATEOAS: Link para el inventario actualizado
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(InventarioController.class).obtener(id)).withSelfRel();
        actualizado.add(selfLink);
        return ResponseEntity.ok(actualizado);
    }

    @ApiOperation(value = "Eliminar un inventario por su ID", response = Void.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        boolean eliminado = service.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
