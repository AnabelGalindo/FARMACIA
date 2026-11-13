import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static final String ARCHIVO_CSV = "medicamentos.csv";

    public static void main(String[] args) {
        InventarioController controlador = new InventarioController();
        Path ruta = Paths.get(ARCHIVO_CSV);

        if (Files.exists(ruta)) {
            List<String> errores = new ArrayList<>();
            try {
                int n = controlador.cargarCSV(ruta.toString(), errores);
                System.out.println("Se cargaron " + n + " medicamento(s) desde " + ARCHIVO_CSV);
                errores.forEach(e -> System.out.println("  Advertencia - " + e));
            } catch (IOException e) {
                System.out.println("No se pudo leer " + ARCHIVO_CSV + ": " + e.getMessage());
            }
        } else {
            System.out.println("No existe " + ARCHIVO_CSV + ": se inicia con el inventario vacio.");
        }

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignorada) {
            }
            new VentanaInventario(controlador, ARCHIVO_CSV).setVisible(true);
        });
    }
}
