package com.perfulandia.inventario.controller;

import com.perfulandia.inventario.dto.InventarioDTO;
import com.perfulandia.inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Inventario", description = "Operaciones relacionadas con el inventario")
@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService service;

    @Operation(summary = "Crear un nuevo inventario", description = "Crea un inventario con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Inventario creado correctamente", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = InventarioDTO.class)))
    @PostMapping
    public ResponseEntity<InventarioDTO> crear(@RequestBody InventarioDTO dto) {
        InventarioDTO creado = service.crear(dto);
        // HATEOAS: Link para obtener el inventario recién creado
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(InventarioController.class).obtener(creado.getIdInventario())).withSelfRel();
        creado.add(selfLink);
        return ResponseEntity.created(URI.create("/api/inventario/" + creado.getIdInventario())).body(creado);
    }

    @Operation(summary = "Obtener todos los inventarios", description = "Devuelve una lista con todos los inventarios")
    @ApiResponse(responseCode = "200", description = "Lista de inventarios", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = InventarioDTO.class)))
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

    @Operation(summary = "Obtener un inventario por su ID", description = "Obtiene un inventario por su ID")
    @ApiResponse(responseCode = "200", description = "Inventario encontrado", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = InventarioDTO.class)))
    @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
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

    @Operation(summary = "Actualizar un inventario por su ID", description = "Actualiza los datos de un inventario existente")
    @ApiResponse(responseCode = "200", description = "Inventario actualizado correctamente", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = InventarioDTO.class)))
    @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
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

    @Operation(summary = "Eliminar un inventario por su ID", description = "Elimina un inventario por su ID")
    @ApiResponse(responseCode = "204", description = "Inventario eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        boolean eliminado = service.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
