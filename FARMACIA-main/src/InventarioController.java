import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controlador que administra el inventario de medicamentos de la farmacia.
 * Usa una colecccion ArrayList como almacenamiento principal y un HashMap
 * para agrupar medicamentos por categoria.
 */
public class InventarioController {

    private ArrayList<Medicamento> medicamentos;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public InventarioController() {
        medicamentos = new ArrayList<>();
    }

    // ---------- Sobrecarga de metodos: agregarMedicamento ----------

    /** Version 1: recibe directamente un objeto Medicamento ya construido. */
    public void agregarMedicamento(Medicamento m) {
        medicamentos.add(m);
    }

    /** Version 2 (sobrecargada): recibe los datos sueltos y la fecha como texto. */
    public void agregarMedicamento(int id, String nombre, String categoria, double precio,
                                    int cantidad, String fechaVencimientoTexto) {
        LocalDate fecha = LocalDate.parse(fechaVencimientoTexto, FORMATO_FECHA);
        Medicamento nuevo = new Medicamento(id, nombre, categoria, precio, cantidad, fecha);
        agregarMedicamento(nuevo);
    }

    // ---------- Sobrecarga de metodos: buscarMedicamento ----------

    /** Version 1: busca por id (int). */
    public Medicamento buscarMedicamento(int id) throws MedicamentoNoEncontradoException {
        for (Medicamento m : medicamentos) {
            if (m.getId() == id) {
                return m;
            }
        }
        throw new MedicamentoNoEncontradoException("No existe un medicamento con id " + id);
    }

    /** Version 2 (sobrecargada): busca por nombre (String), ignorando mayusculas/minusculas. */
    public Medicamento buscarMedicamento(String nombre) throws MedicamentoNoEncontradoException {
        for (Medicamento m : medicamentos) {
            if (m.getNombre().equalsIgnoreCase(nombre)) {
                return m;
            }
        }
        throw new MedicamentoNoEncontradoException("No existe un medicamento con nombre '" + nombre + "'");
    }

    // ---------- Venta con manejo de excepciones ----------

    public void venderMedicamento(int id, int cantidadVendida)
            throws MedicamentoNoEncontradoException, StockInsuficienteException, MedicamentoVencidoException {

        Medicamento medicamento = buscarMedicamento(id);

        if (medicamento.estaVencido()) {
            throw new MedicamentoVencidoException(
                    "El medicamento '" + medicamento.getNombre() + "' esta vencido y no se puede vender.");
        }

        if (medicamento.getCantidad() < cantidadVendida) {
            throw new StockInsuficienteException(
                    "Stock insuficiente para '" + medicamento.getNombre() + "'. Disponible: "
                            + medicamento.getCantidad() + ", solicitado: " + cantidadVendida);
        }

        medicamento.setCantidad(medicamento.getCantidad() - cantidadVendida);
        System.out.println("Venta realizada: " + cantidadVendida + " unidad(es) de " + medicamento.getNombre());
    }

    // ---------- Listados ----------

    public void listarMedicamentos() {
        if (medicamentos.isEmpty()) {
            System.out.println("No hay medicamentos registrados.");
            return;
        }
        for (Medicamento m : medicamentos) {
            System.out.println(m);
        }
    }

    public void listarProximosAVencer(int diasLimite) {
        boolean hayResultados = false;
        for (Medicamento m : medicamentos) {
            if (m.venceEn(diasLimite)) {
                System.out.println(m);
                hayResultados = true;
            }
        }
        if (!hayResultados) {
            System.out.println("No hay medicamentos que venzan en los proximos " + diasLimite + " dias.");
        }
    }

    /** Agrupa los medicamentos por categoria usando un HashMap<String, ArrayList<Medicamento>>. */
    public Map<String, ArrayList<Medicamento>> agruparPorCategoria() {
        Map<String, ArrayList<Medicamento>> agrupado = new HashMap<>();
        for (Medicamento m : medicamentos) {
            agrupado.computeIfAbsent(m.getCategoria(), k -> new ArrayList<>()).add(m);
        }
        return agrupado;
    }


    // ---------- Practica de archivos: guardar y cargar inventario ----------

    /**
     * Guarda el inventario en un archivo de texto delimitado por punto y coma.
     * Cada linea representa un medicamento.
     */
    public void guardarInventario(String nombreArchivo) throws IOException {
        Path ruta = Paths.get(nombreArchivo);
        try (BufferedWriter escritor = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {
            for (Medicamento m : medicamentos) {
                escritor.write(m.getId() + ";" + limpiarCampo(m.getNombre()) + ";"
                        + limpiarCampo(m.getCategoria()) + ";" + m.getPrecio() + ";"
                        + m.getCantidad() + ";" + m.getFechaVencimiento());
                escritor.newLine();
            }
        }
    }

    /**
     * Carga los medicamentos desde el archivo y los agrega al inventario actual.
     * El archivo debe tener los campos separados por punto y coma.
     */
    public void cargarInventario(String nombreArchivo) throws IOException {
        Path ruta = Paths.get(nombreArchivo);
        if (!Files.exists(ruta)) {
            throw new IOException("No existe el archivo: " + ruta.toAbsolutePath());
        }

        try (BufferedReader lector = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] campos = linea.split(";", -1);
                if (campos.length != 6) {
                    throw new IOException("Formato incorrecto en la linea: " + linea);
                }

                try {
                    int id = Integer.parseInt(campos[0]);
                    String nombre = campos[1];
                    String categoria = campos[2];
                    double precio = Double.parseDouble(campos[3]);
                    int cantidad = Integer.parseInt(campos[4]);
                    LocalDate vencimiento = LocalDate.parse(campos[5]);
                    agregarMedicamento(new Medicamento(id, nombre, categoria, precio, cantidad, vencimiento));
                } catch (RuntimeException e) {
                    throw new IOException("No se pudo interpretar la linea: " + linea, e);
                }
            }
        }
    }

    private String limpiarCampo(String valor) {
        return valor.replace(";", ",").replace("\\n", " ").replace("\\r", " ");
    }

}
