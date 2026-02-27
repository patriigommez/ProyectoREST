package dam.patricia.client;

import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import dam.patricia.client.dto.CategoriaDTO;
import dam.patricia.client.dto.ProductoRequestDTO;
import dam.patricia.client.dto.ProductoResponseDTO;

public class Main {

    public static void main(String[] args) {
        ApiClient api = new ApiClient("http://localhost:12345");
        ObjectMapper mapper = new ObjectMapper();
        Scanner sc = new Scanner(System.in);

        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== CLIENTE REST (MENU) ===");
            System.out.println("1) CRUD Categorias");
            System.out.println("2) CRUD Productos");
            System.out.println("0) Salir");
            System.out.print("Opcion: ");
            opcion = leerEntero(sc);

            try {
                if (opcion == 1) menuCategorias(api, mapper, sc);
                if (opcion == 2) menuProductos(api, mapper, sc);
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        sc.close();
        System.out.println("Fin.");
    }

    private static void menuCategorias(ApiClient api, ObjectMapper mapper, Scanner sc) throws Exception {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- CATEGORIAS ---");
            System.out.println("1) Listar");
            System.out.println("2) Crear");
            System.out.println("3) Modificar");
            System.out.println("4) Borrar");
            System.out.println("0) Volver");
            System.out.print("Opcion: ");
            op = leerEntero(sc);

            if (op == 1) {
                String json = api.get("/api/categorias");
                CategoriaDTO[] arr = mapper.readValue(json, CategoriaDTO[].class);
                if (arr.length == 0) System.out.println("(sin categorias)");
                for (CategoriaDTO c : arr) {
                    System.out.println(c.id + " | " + c.nombre + " | " + c.descripcion);
                }
            }

            if (op == 2) {
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Descripcion: ");
                String desc = sc.nextLine();

                CategoriaDTO c = new CategoriaDTO();
                c.nombre = nombre;
                c.descripcion = desc;

                String body = mapper.writeValueAsString(c);
                String res = api.post("/api/categorias", body);
                System.out.println("Creada: " + res);
            }

            if (op == 3) {
                System.out.print("ID a modificar: ");
                long id = leerLong(sc);

                System.out.print("Nuevo nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Nueva descripcion: ");
                String desc = sc.nextLine();

                CategoriaDTO c = new CategoriaDTO();
                c.nombre = nombre;
                c.descripcion = desc;

                String body = mapper.writeValueAsString(c);
                String res = api.put("/api/categorias/" + id, body);
                System.out.println("Actualizada: " + res);
            }

            if (op == 4) {
                System.out.print("ID a borrar: ");
                long id = leerLong(sc);
                api.delete("/api/categorias/" + id);
                System.out.println("Borrada.");
            }
        }
    }

    private static void menuProductos(ApiClient api, ObjectMapper mapper, Scanner sc) throws Exception {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- PRODUCTOS ---");
            System.out.println("1) Listar");
            System.out.println("2) Crear");
            System.out.println("3) Modificar");
            System.out.println("4) Borrar");
            System.out.println("0) Volver");
            System.out.print("Opcion: ");
            op = leerEntero(sc);

            if (op == 1) {
                String json = api.get("/api/productos");
                ProductoResponseDTO[] arr = mapper.readValue(json, ProductoResponseDTO[].class);
                if (arr.length == 0) System.out.println("(sin productos)");
                for (ProductoResponseDTO p : arr) {
                    System.out.println(p.id + " | " + p.nombre + " | " + p.precio
                            + " | cat=" + p.categoriaId + " (" + p.categoriaNombre + ")");
                }
            }

            if (op == 2) {
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Precio: ");
                double precio = leerDouble(sc);
                System.out.print("CategoriaId: ");
                long categoriaId = leerLong(sc);

                ProductoRequestDTO req = new ProductoRequestDTO();
                req.nombre = nombre;
                req.precio = precio;
                req.categoriaId = categoriaId;

                String body = mapper.writeValueAsString(req);
                String res = api.post("/api/productos", body);
                System.out.println("Creado: " + res);
            }

            if (op == 3) {
                System.out.print("ID a modificar: ");
                long id = leerLong(sc);

                System.out.print("Nuevo nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Nuevo precio: ");
                double precio = leerDouble(sc);
                System.out.print("Nueva categoriaId: ");
                long categoriaId = leerLong(sc);

                ProductoRequestDTO req = new ProductoRequestDTO();
                req.nombre = nombre;
                req.precio = precio;
                req.categoriaId = categoriaId;

                String body = mapper.writeValueAsString(req);
                String res = api.put("/api/productos/" + id, body);
                System.out.println("Actualizado: " + res);
            }

            if (op == 4) {
                System.out.print("ID a borrar: ");
                long id = leerLong(sc);
                api.delete("/api/productos/" + id);
                System.out.println("Borrado.");
            }
        }
    }

    private static int leerEntero(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    private static long leerLong(Scanner sc) {
        while (true) {
            try {
                return Long.parseLong(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Numero no valido, repite: ");
            }
        }
    }

    private static double leerDouble(Scanner sc) {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Numero no valido, repite: ");
            }
        }
    }
}