package dam.patricia.client;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import dam.patricia.client.dto.ProductoRequestDTO;

public class ClientJsonTest {

    @Test
    void convierteProductoRequestAJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProductoRequestDTO req = new ProductoRequestDTO();
        req.nombre = "Agua";
        req.precio = 1.0;
        req.categoriaId = 2L;

        String json = mapper.writeValueAsString(req);

        assertTrue(json.contains("Agua"));
        assertTrue(json.contains("categoriaId"));
    }
}