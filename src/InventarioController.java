import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        // Total de medicamentos por ID
        int i = 0;
        for (Medicamento m : medicamentos) {
            i++;
        }
        String[] ListaElementos = new String[i+1];

        System.out.println("Total de medicamentos por ID: " + i);
        // Ordenamiento de elementos
        for (Medicamento m : medicamentos) {
            ListaElementos[m.getId()] = m.toString();
        }
        for (int j = 1; j < ListaElementos.length; j++) {
            System.out.println(ListaElementos[j]);
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
}
