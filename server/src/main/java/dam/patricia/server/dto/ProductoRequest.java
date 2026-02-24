package dam.patricia.server.dto;

public class ProductoRequest {
    private String nombre;
    private double precio;
    private Long categoriaId;

    public ProductoRequest() {}

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public Long getCategoriaId() { return categoriaId; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
}