package dam.patricia.server.dto;

public class ProductoResponse {
    private Long id;
    private String nombre;
    private double precio;
    private Long categoriaId;
    private String categoriaNombre;

    public ProductoResponse(Long id, String nombre, double precio, Long categoriaId, String categoriaNombre) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public Long getCategoriaId() { return categoriaId; }
    public String getCategoriaNombre() { return categoriaNombre; }
}