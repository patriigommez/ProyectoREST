package dam.patricia.server.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import dam.patricia.server.entity.Categoria;
import dam.patricia.server.repository.CategoriaRepository;

public class CategoriaControllerTest {

    @Test
    void crearCategoria_nombreVacio_devuelve400() {
        CategoriaRepository repo = mock(CategoriaRepository.class);
        CategoriaController controller = new CategoriaController(repo);

        Categoria c = new Categoria();
        c.setNombre(""); // invalido
        c.setDescripcion("x");

        ResponseEntity<Categoria> res = controller.crear(c);

        assertEquals(400, res.getStatusCode().value());
        verify(repo, never()).save(any());
    }

    @Test
    void modificarCategoria_inexistente_devuelve404() {
        CategoriaRepository repo = mock(CategoriaRepository.class);
        when(repo.findById(99L)).thenReturn(Optional.empty());

        CategoriaController controller = new CategoriaController(repo);

        Categoria cambios = new Categoria();
        cambios.setNombre("Nueva");
        cambios.setDescripcion("Desc");

        ResponseEntity<Categoria> res = controller.modificar(99L, cambios);

        assertEquals(404, res.getStatusCode().value());
        verify(repo, never()).save(any());
    }

    @Test
    void borrarCategoria_inexistente_devuelve404() {
        CategoriaRepository repo = mock(CategoriaRepository.class);
        when(repo.existsById(50L)).thenReturn(false);

        CategoriaController controller = new CategoriaController(repo);

        ResponseEntity<Void> res = controller.borrar(50L);

        assertEquals(404, res.getStatusCode().value());
        verify(repo, never()).deleteById(anyLong());
    }
}