import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
 
/**
 * FarmaStock - Prototipo inicial
 * Sistema de control de inventario y vencimiento de medicamentos
 * para una pequenia farmacia de barrio.
 */
public class Main {
    public static void main(String[] args) {
 
        InventarioController inventario = new InventarioController();
 
        System.out.println("=================");
        System.out.println(" FARMASTOCK");
        System.out.println("=================\n");
 
        // --- Registro de medicamentos usando las dos versiones sobrecargadas ---
 
        // Version 1: objeto Medicamento ya construido
        inventario.agregarMedicamento(
                new Medicamento(1, "Paracetamol 500mg", "Analgesico", 5.50, 40,
                        LocalDate.now().plusDays(200)));
 
        inventario.agregarMedicamento(
                new Medicamento(5, "Loratadina 10mg", "Antialergico", 4.80, 30,
                        LocalDate.now().plusDays(300)));
 
        inventario.agregarMedicamento(
                new Medicamento(6, "Omeprazol 20mg", "Gastrico", 6.50, 25,
                        LocalDate.now().plusDays(180)));
 
        // Version 2 (sobrecargada): datos sueltos + fecha como texto
        inventario.agregarMedicamento(2, "Amoxicilina 500mg", "Antibiotico", 12.90, 15,
                LocalDate.now().plusDays(10).toString());
        inventario.agregarMedicamento(3, "Ibuprofeno 400mg", "Antiinflamatorio", 7.20, 5,
                LocalDate.now().plusDays(5).toString());
        inventario.agregarMedicamento(4, "Suero Oral", "Rehidratante", 3.00, 20,
                LocalDate.now().minusDays(3).toString()); // ya vencido a proposito
 
        System.out.println(">> Inventario actual:");
        inventario.listarMedicamentos();
 
        // --- Busqueda usando ambas versiones sobrecargadas de buscarMedicamento ---
        System.out.println("\n>> Busqueda por id y por nombre:");
        try {
            Medicamento porId = inventario.buscarMedicamento(2);
            System.out.println("Encontrado por id: " + porId.getNombre());
 
            Medicamento porNombre = inventario.buscarMedicamento("Ibuprofeno 400mg");
            System.out.println("Encontrado por nombre: " + porNombre.getNombre());
        } catch (MedicamentoNoEncontradoException e) {
            System.out.println("Error en la busqueda: " + e.getMessage());
        }
 
        // --- Ventas: dos correctas y tres escenarios de excepcion ---
        System.out.println("\n>> Procesando ventas:");
 
        // Venta correcta
        try {
            inventario.venderMedicamento(1, 10);
        } catch (MedicamentoNoEncontradoException | StockInsuficienteException | MedicamentoVencidoException e) {
            System.out.println("Error en la venta: " + e.getMessage());
        }
 
        // Venta correcta
        try {
            inventario.venderMedicamento(5, 20);
        } catch (MedicamentoNoEncontradoException | StockInsuficienteException | MedicamentoVencidoException e) {
            System.out.println("Error en la venta: " + e.getMessage());
        }
 
        // Venta correcta
        try {
            inventario.venderMedicamento(6, 15);
        } catch (MedicamentoNoEncontradoException | StockInsuficienteException | MedicamentoVencidoException e) {
            System.out.println("Error en la venta: " + e.getMessage());
        }
 
        // Provoca StockInsuficienteException (hay 5, se piden 50)
        try {
            inventario.venderMedicamento(3, 50);
        } catch (MedicamentoNoEncontradoException | StockInsuficienteException | MedicamentoVencidoException e) {
            System.out.println("Error en la venta: " + e.getMessage());
        }
 
        // Provoca MedicamentoVencidoException (Suero Oral esta vencido)
        try {
            inventario.venderMedicamento(4, 1);
        } catch (MedicamentoNoEncontradoException | StockInsuficienteException | MedicamentoVencidoException e) {
            System.out.println("Error en la venta: " + e.getMessage());
        }
 
        // Provoca MedicamentoNoEncontradoException (el id 99 no existe)
        try {
            inventario.venderMedicamento(99, 1);
        } catch (MedicamentoNoEncontradoException | StockInsuficienteException | MedicamentoVencidoException e) {
            System.out.println("Error en la venta: " + e.getMessage());
        }
 
        System.out.println("\n>> Inventario despues de las ventas:");
        inventario.listarMedicamentos();
 
        // --- Medicamentos proximos a vencer ---
        System.out.println("\n>> Medicamentos que vencen en los proximos 15 dias:");
        inventario.listarProximosAVencer(15);
 
        // --- Agrupacion por categoria usando HashMap ---
        System.out.println("\n>> Medicamentos agrupados por categoria:");
        Map<String, ArrayList<Medicamento>> agrupados = inventario.agruparPorCategoria();
        for (String categoria : agrupados.keySet()) {
            System.out.println("- " + categoria + ":");
            for (Medicamento m : agrupados.get(categoria)) {
                System.out.println("    " + m.getNombre());
            }
        }
    }
}