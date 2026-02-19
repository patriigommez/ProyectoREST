package dam.patricia.server.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dam.patricia.server.entity.Categoria;
import dam.patricia.server.repository.CategoriaRepository;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // 1) LISTAR TODAS
    @GetMapping
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    // 2) CREAR NUEVA
    @PostMapping
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria) {
        if (categoria == null || categoria.getNombre() == null || categoria.getNombre().equals("")) {
            return ResponseEntity.badRequest().build();
        }

        Categoria guardada = categoriaRepository.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    // 3) MODIFICAR EXISTENTE
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> modificar(@PathVariable("id") Long id, @RequestBody Categoria cambios) {

        if (cambios == null || cambios.getNombre() == null || cambios.getNombre().equals("")) {
            return ResponseEntity.badRequest().build();
        }

        Categoria existing = categoriaRepository.findById(id).orElse(null);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setNombre(cambios.getNombre());
        existing.setDescripcion(cambios.getDescripcion());

        Categoria actualizada = categoriaRepository.save(existing);
        return ResponseEntity.ok(actualizada);
    }

    // 4) BORRAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable("id") Long id) {
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

