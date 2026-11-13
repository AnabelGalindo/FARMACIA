import edu.upn.farmacia.exceptions.MedicamentoNoEncontradoException;
import edu.upn.farmacia.exceptions.MedicamentoVencidoException;
import edu.upn.farmacia.exceptions.StockInsuficienteException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Controlador que administra el inventario de medicamentos de la farmacia.
 * Usa una coleccion ArrayList como almacenamiento principal y un HashMap
 * para agrupar medicamentos por categoria. Ademas persiste el inventario
 * en un archivo CSV (que funciona como una "base de datos" sencilla).
 */
public class InventarioController {

    private ArrayList<Medicamento> medicamentos;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String ENCABEZADO_CSV = "id,nombre,categoria,precio,cantidad,fechaVencimiento";

    public InventarioController() {
        medicamentos = new ArrayList<>();
    }

    // Acceso a la coleccion 

    /* 
    * Vista de solo lectura de los medicamentos (la usa la interfaz Swing).
    */
    public List<Medicamento> getMedicamentos() {
        return Collections.unmodifiableList(medicamentos);
    }

    public boolean existeId(int id) {
        for (Medicamento m : medicamentos) {
            if (m.getId() == id) {
                return true;
            }
        }
        return false;
    }


    public void agregarMedicamento(Medicamento m) {
        if (existeId(m.getId())) {
            throw new IllegalArgumentException("Ya existe un medicamento con id " + m.getId());
        }
        medicamentos.add(m);
    }

    public void agregarMedicamento(int id, String nombre, String categoria, double precio,
                                    int cantidad, String fechaVencimientoTexto) {
        LocalDate fecha = LocalDate.parse(fechaVencimientoTexto, FORMATO_FECHA);
        Medicamento nuevo = new Medicamento(id, nombre, categoria, precio, cantidad, fecha);
        agregarMedicamento(nuevo);
    }

    public void eliminarMedicamento(int id) throws MedicamentoNoEncontradoException {
        medicamentos.remove(buscarMedicamento(id));
    }


    public Medicamento buscarMedicamento(int id) throws MedicamentoNoEncontradoException {
        for (Medicamento m : medicamentos) {
            if (m.getId() == id) {
                return m;
            }
        }
        throw new MedicamentoNoEncontradoException("No existe un medicamento con id " + id);
    }

    public Medicamento buscarMedicamento(String nombre) throws MedicamentoNoEncontradoException {
        for (Medicamento m : medicamentos) {
            if (m.getNombre().equalsIgnoreCase(nombre)) {
                return m;
            }
        }
        throw new MedicamentoNoEncontradoException("No existe un medicamento con nombre '" + nombre + "'");
    }


    public void venderMedicamento(int id, int cantidadVendida)
            throws MedicamentoNoEncontradoException, StockInsuficienteException, MedicamentoVencidoException {

        if (cantidadVendida <= 0) {
            throw new IllegalArgumentException("La cantidad a vender debe ser mayor que cero.");
        }

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


    /*
     * Lista los medicamentos ordenados por id.
     */
    public void listarMedicamentos() {
        if (medicamentos.isEmpty()) {
            System.out.println("No hay medicamentos registrados.");
            return;
        }
        List<Medicamento> ordenados = new ArrayList<>(medicamentos);
        ordenados.sort(Comparator.comparingInt(Medicamento::getId));

        System.out.println("Total de medicamentos: " + ordenados.size());
        for (Medicamento m : ordenados) {
            System.out.println(m);
        }
    }

    public List<Medicamento> obtenerProximosAVencer(int diasLimite) {
        List<Medicamento> resultado = new ArrayList<>();
        for (Medicamento m : medicamentos) {
            if (m.venceEn(diasLimite)) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    public void listarProximosAVencer(int diasLimite) {
        List<Medicamento> proximos = obtenerProximosAVencer(diasLimite);
        if (proximos.isEmpty()) {
            System.out.println("No hay medicamentos que venzan en los proximos " + diasLimite + " dias.");
            return;
        }
        for (Medicamento m : proximos) {
            System.out.println(m);
        }
    }

    /* 
    * Agrupa los medicamentos por categoria usando un HashMap<. 
    */
    public Map<String, ArrayList<Medicamento>> agruparPorCategoria() {
        Map<String, ArrayList<Medicamento>> agrupado = new HashMap<>();
        for (Medicamento m : medicamentos) {
            agrupado.computeIfAbsent(m.getCategoria(), k -> new ArrayList<>()).add(m);
        }
        return agrupado;
    }



    /* 
    * Guarda todo el inventario en un archivo CSV. 
    */
    public void guardarCSV(String ruta) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(Paths.get(ruta), StandardCharsets.UTF_8)) {
            w.write(ENCABEZADO_CSV);
            w.newLine();
            for (Medicamento m : medicamentos) {
                w.write(m.getId() + ","
                        + escaparCSV(m.getNombre()) + ","
                        + escaparCSV(m.getCategoria()) + ","
                        + m.getPrecio() + ","
                        + m.getCantidad() + ","
                        + m.getFechaVencimiento().format(FORMATO_FECHA));
                w.newLine();
            }
        }
    }

    /*
     * Importa medicamentos desde un CSV y REEMPLAZA el inventario actual.

     * @return cantidad de medicamentos cargados correctamente
     */
    
    public int cargarCSV(String ruta, List<String> errores) throws IOException {
        List<Medicamento> cargados = new ArrayList<>();
        Set<Integer> idsVistos = new HashSet<>();

        try (BufferedReader r = Files.newBufferedReader(Paths.get(ruta), StandardCharsets.UTF_8)) {
            String linea;
            int numeroLinea = 0;
            while ((linea = r.readLine()) != null) {
                numeroLinea++;
                if (numeroLinea == 1 && linea.startsWith("\uFEFF")) {
                    linea = linea.substring(1); // quita BOM si existe
                }
                if (linea.isBlank() || (numeroLinea == 1 && linea.toLowerCase().startsWith("id"))) {
                    continue; // linea vacia o encabezado
                }
                try {
                    List<String> c = parsearLineaCSV(linea);
                    if (c.size() != 6) {
                        throw new IllegalArgumentException("se esperaban 6 columnas y hay " + c.size());
                    }
                    int id = Integer.parseInt(c.get(0).trim());
                    if (!idsVistos.add(id)) {
                        throw new IllegalArgumentException("id duplicado " + id);
                    }
                    String nombre = c.get(1).trim();
                    String categoria = c.get(2).trim();
                    double precio = Double.parseDouble(c.get(3).trim());
                    int cantidad = Integer.parseInt(c.get(4).trim());
                    LocalDate fecha = LocalDate.parse(c.get(5).trim(), FORMATO_FECHA);
                    cargados.add(new Medicamento(id, nombre, categoria, precio, cantidad, fecha));
                } catch (RuntimeException e) { // NumberFormat, DateTimeParse, IllegalArgument
                    errores.add("Linea " + numeroLinea + ": " + e.getMessage());
                }
            }
        }

        medicamentos.clear();
        medicamentos.addAll(cargados);
        return cargados.size();
    }

    /* 
    * Si el texto tiene coma, comillas o salto de linea, lo encierra entre comillas.
    */
    private static String escaparCSV(String texto) {
        if (texto.contains(",") || texto.contains("\"") || texto.contains("\n")) {
            return "\"" + texto.replace("\"", "\"\"") + "\"";
        }
        return texto;
    }

    /* 
    * Divide una linea CSV respetando campos entre comillas.
    */
    private static List<String> parsearLineaCSV(String linea) {
        List<String> campos = new ArrayList<>();
        StringBuilder actual = new StringBuilder();
        boolean entreComillas = false;
        for (int i = 0; i < linea.length(); i++) {
            char ch = linea.charAt(i);
            if (entreComillas) {
                if (ch == '"') {
                    if (i + 1 < linea.length() && linea.charAt(i + 1) == '"') {
                        actual.append('"');
                        i++;
                    } else {
                        entreComillas = false;
                    }
                } else {
                    actual.append(ch);
                }
            } else if (ch == '"') {
                entreComillas = true;
            } else if (ch == ',') {
                campos.add(actual.toString());
                actual.setLength(0);
            } else {
                actual.append(ch);
            }
        }
        campos.add(actual.toString());
        return campos;
    }
}
