import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class Main {
  public static void main(String[] args) {

    InventarioController inventario = new InventarioController();

    mostrarEncabezado();

    // ============================================================

    // 1. REGISTRO DE MEDICAMENTOS

    // ============================================================

    mostrarSeccion("1. REGISTRO DE MEDICAMENTOS");

    // ID 1

    inventario.agregarMedicamento(new Medicamento(1,"Paracetamol 500mg","Analgesico",5.50,40,LocalDate.now().plusDays(200)));

    // ID 2

    inventario.agregarMedicamento(2,"Amoxicilina 500mg","Antibiotico",12.90,15,LocalDate.now().plusDays(10).toString());

    // ID 3

    inventario.agregarMedicamento(3,"Ibuprofeno 400mg","Antiinflamatorio",7.20,5,LocalDate.now().plusDays(5).toString());

    // ID 4: medicamento vencido para probar la validacion

    inventario.agregarMedicamento(4,"Suero Oral","Rehidratante",3.00,20,LocalDate.now().minusDays(3).toString());

    // ID 5

    inventario.agregarMedicamento(new Medicamento(5,"Loratadina 10mg","Antialergico",4.80,30,LocalDate.now().plusDays(300)));

    // ID 6

    inventario.agregarMedicamento(new Medicamento(6,"Omeprazol 20mg","Gastrico",6.50,25,LocalDate.now().plusDays(180)));

    System.out.println("Se registraron 6 medicamentos correctamente.");
    
    // ============================================================

    // 2. CONSULTA DEL INVENTARIO

    // ============================================================

    mostrarSeccion("2. INVENTARIO ACTUAL");

    inventario.listarMedicamentos();

    // ============================================================

    // 3. BUSQUEDA DE MEDICAMENTOS

    // ============================================================

    mostrarSeccion("3. BUSQUEDA DE MEDICAMENTOS");

    System.out.println("Busqueda por ID:");

    try {

      Medicamento medicamento = inventario.buscarMedicamento(2);

      System.out.println("ID: " + medicamento.getId());
      System.out.println("Nombre: " + medicamento.getNombre());
      System.out.println("Categoria: " + medicamento.getCategoria());

    } catch (MedicamentoNoEncontradoException e) {

      System.out.println("Error: " + e.getMessage());

    }

    System.out.println();
    System.out.println("Busqueda por nombre:");

    try {

      Medicamento medicamento = inventario.buscarMedicamento("Ibuprofeno 400mg");
      System.out.println("Medicamento encontrado: " + medicamento.getNombre());

    } catch (MedicamentoNoEncontradoException e) {

      System.out.println("Error: " + e.getMessage());

    }

    // ============================================================

    // 4. PROCESAMIENTO DE VENTAS

    // ============================================================

    mostrarSeccion("4. PROCESAMIENTO DE VENTAS");

    // Ventas validas

    realizarVenta(inventario, 1, 10);
    realizarVenta(inventario, 5, 20);
    realizarVenta(inventario, 6, 15);

    // Prueba de stock insuficiente

    realizarVenta(inventario, 3, 50);

    // Prueba de medicamento vencido

    realizarVenta(inventario, 4, 1);

    // Prueba de medicamento inexistente

    realizarVenta(inventario, 99, 1);

    // ============================================================

    // 5. INVENTARIO DESPUES DE LAS VENTAS

    // ============================================================

    mostrarSeccion("5. INVENTARIO DESPUES DE LAS VENTAS");

    inventario.listarMedicamentos();

    // ============================================================

    // 6. MEDICAMENTOS PROXIMOS A VENCER

    // ============================================================

    mostrarSeccion("6. MEDICAMENTOS PROXIMOS A VENCER");

    System.out.println("Medicamentos que vencen dentro de los proximos 15 dias:");
    System.out.println();

    inventario.listarProximosAVencer(15);

    // ============================================================

    // 7. AGRUPACION POR CATEGORIA

    // ============================================================

    mostrarSeccion("7. MEDICAMENTOS AGRUPADOS POR CATEGORIA");

    Map<String, ArrayList<Medicamento>> agrupados =

        inventario.agruparPorCategoria();

    for (String categoria : agrupados.keySet()) {

      System.out.println("Categoria: " + categoria);

      for (Medicamento medicamento : agrupados.get(categoria)) {

        System.out.println(

            " - " + medicamento.getNombre()
        );
      }
      System.out.println();

    }

    // ============================================================

    // FINAL DE LA DEMOSTRACION

    // ============================================================

    System.out.println();
    System.out.println("============================================================");
    System.out.println("         FIN DE LA DEMOSTRACION");
    System.out.println("============================================================");
    System.out.println();
    System.out.println("FarmaStock");
    System.out.println("Sistema de control de inventario y vencimiento de medicamentos.");
  }

  // ================================================================

  // METODO PARA REALIZAR UNA VENTA

  // ================================================================

  private static void realizarVenta(InventarioController inventario, int id, int cantidad) {

    System.out.println("----------------------------------------");

    try {

      Medicamento medicamento = inventario.buscarMedicamento(id);

      System.out.println("Venta solicitada");
      System.out.println("Producto: " + medicamento.getNombre());
      System.out.println("Cantidad: " + cantidad);

      inventario.venderMedicamento(id, cantidad);

      Medicamento actualizado = inventario.buscarMedicamento(id);

      System.out.println("Venta procesada correctamente.");
      System.out.println("Stock restante: " + actualizado.getCantidad());

    } catch (MedicamentoNoEncontradoException

        | StockInsuficienteException
        | MedicamentoVencidoException e) {

      System.out.println("Venta rechazada.");
      System.out.println("Motivo: " + e.getMessage());
    }

    System.out.println("----------------------------------------");
    System.out.println();
  }

  // ================================================================

  // METODO PARA MOSTRAR EL ENCABEZADO

  // ================================================================

  private static void mostrarEncabezado() {

    System.out.println();
    System.out.println("============================================================");
    System.out.println("                       FARMASTOCK");
    System.out.println("              Sistema de Control de Inventario");
    System.out.println("               y Vencimiento de Medicamentos");
    System.out.println("============================================================");
    System.out.println();
  }

  // ================================================================

  // METODO PARA MOSTRAR LAS SECCIONES

  // ================================================================

  private static void mostrarSeccion(String texto) {
    System.out.println();
    System.out.println("------------------------------------------------------------");
    System.out.println(texto);
    System.out.println("------------------------------------------------------------");
    System.out.println();
  }

}