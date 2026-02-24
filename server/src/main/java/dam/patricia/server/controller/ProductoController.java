package dam.patricia.server.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dam.patricia.server.dto.ProductoRequest;
import dam.patricia.server.dto.ProductoResponse;
import dam.patricia.server.entity.Categoria;
import dam.patricia.server.entity.Producto;
import dam.patricia.server.repository.CategoriaRepository;
import dam.patricia.server.repository.ProductoRepository;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoController(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // 1) LISTAR
    @GetMapping
    public List<ProductoResponse> listar() {
        return productoRepository.findAll().stream()
                .map(p -> new ProductoResponse(
                        p.getId(),
                        p.getNombre(),
                        p.getPrecio(),
                        p.getCategoria().getId(),
                        p.getCategoria().getNombre()
                ))
                .toList();
    }

    // 2) CREAR
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@RequestBody ProductoRequest req) {
        if (req == null || req.getNombre() == null || req.getNombre().equals("") || req.getCategoriaId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Categoria categoria = categoriaRepository.findById(req.getCategoriaId()).orElse(null);
        if (categoria == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Producto p = new Producto();
        p.setNombre(req.getNombre());
        p.setPrecio(req.getPrecio());
        p.setCategoria(categoria);

        Producto guardado = productoRepository.save(p);

        ProductoResponse res = new ProductoResponse(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getPrecio(),
                guardado.getCategoria().getId(),
                guardado.getCategoria().getNombre()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    // 3) MODIFICAR
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> modificar(@PathVariable("id") Long id, @RequestBody ProductoRequest req) {
        if (req == null || req.getNombre() == null || req.getNombre().equals("") || req.getCategoriaId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Producto existing = productoRepository.findById(id).orElse(null);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        Categoria categoria = categoriaRepository.findById(req.getCategoriaId()).orElse(null);
        if (categoria == null) {
            return ResponseEntity.badRequest().build();
        }

        existing.setNombre(req.getNombre());
        existing.setPrecio(req.getPrecio());
        existing.setCategoria(categoria);

        Producto actualizado = productoRepository.save(existing);

        ProductoResponse res = new ProductoResponse(
                actualizado.getId(),
                actualizado.getNombre(),
                actualizado.getPrecio(),
                actualizado.getCategoria().getId(),
                actualizado.getCategoria().getNombre()
        );

        return ResponseEntity.ok(res);
    }

    // 4) BORRAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable("id") Long id) {
        if (!productoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}