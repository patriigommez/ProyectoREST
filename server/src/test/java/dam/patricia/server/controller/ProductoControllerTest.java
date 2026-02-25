package dam.patricia.server.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import dam.patricia.server.dto.ProductoRequest;
import dam.patricia.server.repository.CategoriaRepository;
import dam.patricia.server.repository.ProductoRepository;

public class ProductoControllerTest {

    @Test
    void crearProducto_categoriaNoExiste_devuelve400() {
        ProductoRepository prodRepo = mock(ProductoRepository.class);
        CategoriaRepository catRepo = mock(CategoriaRepository.class);

        when(catRepo.findById(99L)).thenReturn(Optional.empty());

        ProductoController controller = new ProductoController(prodRepo, catRepo);

        ProductoRequest req = new ProductoRequest();
        req.setNombre("Agua");
        req.setPrecio(1.0);
        req.setCategoriaId(99L);

        ResponseEntity<?> res = controller.crear(req);

        assertEquals(400, res.getStatusCode().value());
        verify(prodRepo, never()).save(any());
    }

    @Test
    void modificarProducto_noExiste_devuelve404() {
        ProductoRepository prodRepo = mock(ProductoRepository.class);
        CategoriaRepository catRepo = mock(CategoriaRepository.class);

        when(prodRepo.findById(10L)).thenReturn(Optional.empty());

        ProductoController controller = new ProductoController(prodRepo, catRepo);

        ProductoRequest req = new ProductoRequest();
        req.setNombre("Nuevo");
        req.setPrecio(2.0);
        req.setCategoriaId(1L);

        ResponseEntity<?> res = controller.modificar(10L, req);

        assertEquals(404, res.getStatusCode().value());
        verify(prodRepo, never()).save(any());
    }

    @Test
    void borrarProducto_existente_devuelve204() {
        ProductoRepository prodRepo = mock(ProductoRepository.class);
        CategoriaRepository catRepo = mock(CategoriaRepository.class);

        when(prodRepo.existsById(1L)).thenReturn(true);

        ProductoController controller = new ProductoController(prodRepo, catRepo);

        ResponseEntity<Void> res = controller.borrar(1L);

        assertEquals(204, res.getStatusCode().value());
        verify(prodRepo, times(1)).deleteById(1L);
    }
}