import com.toedter.calendar.JDateChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 * Interfaz grafica para manejar el inventario de la farmacia.
 *
 * El diseno visual (menu, botones, tabla y barra de estado) se edita en NetBeans
 * con el archivo VentanaInventario.form; la logica esta en los metodos de abajo.
 */
public class VentanaInventario extends javax.swing.JFrame {

    private static final int DIAS_ALERTA = 30;

    private final InventarioController controlador;
    private String rutaCsv; 
    private DefaultTableModel modelo;

    public VentanaInventario(InventarioController controlador, String rutaCsv) {
        this.controlador = controlador;
        this.rutaCsv = rutaCsv;

        initComponents();

        // Configuracion que no se hace desde el diseñador
        modelo = (DefaultTableModel) tabla.getModel();
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setAutoCreateRowSorter(true);
        tabla.setDefaultRenderer(Object.class, new RenderizadorEstado());
        setSize(900, 520);
        setLocationRelativeTo(null);

        mostrarTodos();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBotones = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnVender = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnPorVencer = new javax.swing.JButton();
        btnPorCategoria = new javax.swing.JButton();
        btnGraficos = new javax.swing.JButton();
        btnVerTodos = new javax.swing.JButton();
        btnImportar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        scrollTabla = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        barraEstado = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        miImportar = new javax.swing.JMenuItem();
        miExportar = new javax.swing.JMenuItem();
        miGuardar = new javax.swing.JMenuItem();
        separadorArchivo = new javax.swing.JPopupMenu.Separator();
        miSalir = new javax.swing.JMenuItem();
        menuMedicamentos = new javax.swing.JMenu();
        miAgregar = new javax.swing.JMenuItem();
        miVender = new javax.swing.JMenuItem();
        miEliminar = new javax.swing.JMenuItem();
        miBuscar = new javax.swing.JMenuItem();
        menuReportes = new javax.swing.JMenu();
        miProximos = new javax.swing.JMenuItem();
        miCategoria = new javax.swing.JMenuItem();
        miGraficos = new javax.swing.JMenuItem();
        miVerTodos = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Farmacia UPN - Inventario de medicamentos");
        setPreferredSize(new java.awt.Dimension(900, 520));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarActionPerformed(evt);
            }
        });
        panelBotones.add(btnAgregar);

        btnVender.setText("Vender");
        btnVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                venderActionPerformed(evt);
            }
        });
        panelBotones.add(btnVender);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });
        panelBotones.add(btnEliminar);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });
        panelBotones.add(btnBuscar);

        btnPorVencer.setText("Por vencer");
        btnPorVencer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proximosActionPerformed(evt);
            }
        });
        panelBotones.add(btnPorVencer);

        btnPorCategoria.setText("Por categoria");
        btnPorCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoriaActionPerformed(evt);
            }
        });
        panelBotones.add(btnPorCategoria);

        btnGraficos.setText("Graficos");
        btnGraficos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graficosActionPerformed(evt);
            }
        });
        panelBotones.add(btnGraficos);

        btnVerTodos.setText("Ver todos");
        btnVerTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verTodosActionPerformed(evt);
            }
        });
        panelBotones.add(btnVerTodos);

        btnImportar.setText("Importar CSV");
        btnImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importarActionPerformed(evt);
            }
        });
        panelBotones.add(btnImportar);

        btnExportar.setText("Exportar CSV");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportarActionPerformed(evt);
            }
        });
        panelBotones.add(btnExportar);

        getContentPane().add(menuBar);

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Categoria", "Precio", "Cantidad", "Vencimiento", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setRowHeight(24);
        scrollTabla.setViewportView(tabla);

        getContentPane().add(panelBotones);

        barraEstado.setText(" ");
        getContentPane().add(scrollTabla);

        menuArchivo.setText("Archivo");

        miImportar.setText("Importar CSV...");
        miImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importarActionPerformed(evt);
            }
        });
        menuArchivo.add(miImportar);

        miExportar.setText("Exportar CSV...");
        miExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportarActionPerformed(evt);
            }
        });
        menuArchivo.add(miExportar);

        miGuardar.setText("Guardar");
        miGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });
        menuArchivo.add(miGuardar);
        menuArchivo.add(separadorArchivo);

        miSalir.setText("Salir");
        miSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });
        menuArchivo.add(miSalir);

        menuBar.add(menuArchivo);

        menuMedicamentos.setText("Medicamentos");

        miAgregar.setText("Agregar...");
        miAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarActionPerformed(evt);
            }
        });
        menuMedicamentos.add(miAgregar);

        miVender.setText("Vender...");
        miVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                venderActionPerformed(evt);
            }
        });
        menuMedicamentos.add(miVender);

        miEliminar.setText("Eliminar");
        miEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });
        menuMedicamentos.add(miEliminar);

        miBuscar.setText("Buscar...");
        miBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });
        menuMedicamentos.add(miBuscar);

        menuBar.add(menuMedicamentos);

        menuReportes.setText("Reportes");

        miProximos.setText("Proximos a vencer...");
        miProximos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proximosActionPerformed(evt);
            }
        });
        menuReportes.add(miProximos);

        miCategoria.setText("Agrupar por categoria");
        miCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoriaActionPerformed(evt);
            }
        });
        menuReportes.add(miCategoria);

        miGraficos.setText("Graficos...");
        miGraficos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graficosActionPerformed(evt);
            }
        });
        menuReportes.add(miGraficos);

        miVerTodos.setText("Ver todos");
        miVerTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verTodosActionPerformed(evt);
            }
        });
        menuReportes.add(miVerTodos);

        menuBar.add(menuReportes);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // ---------- Manejadores de eventos (los enlaza el diseñador) ----------

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        salir();
    }//GEN-LAST:event_formWindowClosing

    private void agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarActionPerformed
        agregar();
    }//GEN-LAST:event_agregarActionPerformed

    private void venderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_venderActionPerformed
        vender();
    }//GEN-LAST:event_venderActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_eliminarActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        buscar();
    }//GEN-LAST:event_buscarActionPerformed

    private void proximosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proximosActionPerformed
        proximosAVencer();
    }//GEN-LAST:event_proximosActionPerformed

    private void categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoriaActionPerformed
        agruparPorCategoria();
    }//GEN-LAST:event_categoriaActionPerformed

    private void graficosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficosActionPerformed
        mostrarGraficos();
    }//GEN-LAST:event_graficosActionPerformed

    private void verTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verTodosActionPerformed
        mostrarTodos();
    }//GEN-LAST:event_verTodosActionPerformed

    private void importarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importarActionPerformed
        importarCsv();
    }//GEN-LAST:event_importarActionPerformed

    private void exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportarActionPerformed
        exportarCsv();
    }//GEN-LAST:event_exportarActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        guardar();
    }//GEN-LAST:event_guardarActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        salir();
    }//GEN-LAST:event_salirActionPerformed


    private void cargarTabla(List<Medicamento> lista) {
        modelo.setRowCount(0);
        List<Medicamento> ordenada = new ArrayList<>(lista);
        ordenada.sort(Comparator.comparingInt(Medicamento::getId));
        for (Medicamento m : ordenada) {
            String estado = m.estaVencido() ? "VENCIDO"
                    : m.venceEn(DIAS_ALERTA) ? "Por vencer" : "OK";
            modelo.addRow(new Object[]{
                    m.getId(), m.getNombre(), m.getCategoria(),
                    String.format("S/ %.2f", m.getPrecio()), m.getCantidad(),
                    m.getFechaVencimiento(), estado});
        }
    }

    private void mostrarTodos() {
        cargarTabla(controlador.getMedicamentos());
        estado("Mostrando " + modelo.getRowCount() + " medicamento(s).");
    }

    private void estado(String texto) {
        barraEstado.setText("  " + texto);
    }

    /*
    * Pinta las filas segun el estado: rojo = vencido, naranja = por vencer. 
    */
    private static class RenderizadorEstado extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel,
                                                       boolean foco, int fila, int col) {
            Component c = super.getTableCellRendererComponent(t, v, sel, foco, fila, col);
            if (!sel) {
                Object estado = t.getValueAt(fila, t.getColumnCount() - 1);
                if ("VENCIDO".equals(estado)) {
                    c.setBackground(new Color(255, 205, 205));
                } else if ("Por vencer".equals(estado)) {
                    c.setBackground(new Color(255, 231, 186));
                } else {
                    c.setBackground(Color.WHITE);
                }
                c.setForeground(Color.BLACK);
            }
            return c;
        }
    }


    private void agregar() {
        JTextField id = new JTextField();
        JTextField nombre = new JTextField();
        JTextField categoria = new JTextField();
        JTextField precio = new JTextField();
        JTextField cantidad = new JTextField();
        JDateChooser fecha = new JDateChooser();
        fecha.setDateFormatString("yyyy-MM-dd");
        fecha.setDate(Date.from(LocalDate.now().plusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Object[] campos = {
                "ID:", id, "Nombre:", nombre, "Categoria:", categoria,
                "Precio:", precio, "Cantidad:", cantidad, "Fecha de vencimiento:", fecha};

        if (JOptionPane.showConfirmDialog(this, campos, "Agregar medicamento",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) {
            return;
        }
        if (fecha.getDate() == null) {
            error("Selecciona una fecha de vencimiento.");
            return;
        }
        LocalDate vencimiento = fecha.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        try {
            controlador.agregarMedicamento(
                    Integer.parseInt(id.getText().trim()),
                    nombre.getText().trim(),
                    categoria.getText().trim(),
                    Double.parseDouble(precio.getText().trim()),
                    Integer.parseInt(cantidad.getText().trim()),
                    vencimiento.toString()); // ISO yyyy-MM-dd
            mostrarTodos();
            estado("Medicamento agregado.");
        } catch (NumberFormatException ex) {
            error("ID, precio y cantidad deben ser numeros validos.");
        } catch (DateTimeParseException ex) {
            error("La fecha debe tener el formato yyyy-MM-dd (ej. 2027-01-31).");
        } catch (IllegalArgumentException ex) {
            error(ex.getMessage());
        }
    }

    private void vender() {
        Integer id = idSeleccionadoOPreguntar();
        if (id == null) {
            return;
        }
        String texto = JOptionPane.showInputDialog(this, "Cantidad a vender:", "Vender", JOptionPane.QUESTION_MESSAGE);
        if (texto == null) {
            return;
        }
        try {
            controlador.venderMedicamento(id, Integer.parseInt(texto.trim()));
            mostrarTodos();
            estado("Venta realizada.");
        } catch (NumberFormatException ex) {
            error("La cantidad debe ser un numero entero.");
        } catch (Exception ex) { // no encontrado, vencido, stock insuficiente, cantidad invalida
            error(ex.getMessage());
        }
    }

    private void eliminar() {
        Integer id = idSeleccionadoOPreguntar();
        if (id == null) {
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Eliminar el medicamento con id " + id + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            controlador.eliminarMedicamento(id);
            mostrarTodos();
            estado("Medicamento eliminado.");
        } catch (Exception ex) {
            error(ex.getMessage());
        }
    }

    private void buscar() {
        String texto = JOptionPane.showInputDialog(this, "Buscar por ID o por nombre:", "Buscar",
                JOptionPane.QUESTION_MESSAGE);
        if (texto == null || texto.isBlank()) {
            return;
        }
        texto = texto.trim();
        try {
            Medicamento encontrado;
            try {
                encontrado = controlador.buscarMedicamento(Integer.parseInt(texto)); // sobrecarga int
            } catch (NumberFormatException ex) {
                encontrado = controlador.buscarMedicamento(texto);                  // sobrecarga String
            }
            cargarTabla(List.of(encontrado));
            estado("Resultado de la busqueda. Usa 'Ver todos' para volver al listado completo.");
        } catch (Exception ex) {
            error(ex.getMessage());
        }
    }

    private void proximosAVencer() {
        String texto = JOptionPane.showInputDialog(this, "Dias limite:", String.valueOf(DIAS_ALERTA));
        if (texto == null) {
            return;
        }
        try {
            int dias = Integer.parseInt(texto.trim());
            List<Medicamento> lista = controlador.obtenerProximosAVencer(dias);
            cargarTabla(lista);
            estado(lista.size() + " medicamento(s) vencen en los proximos " + dias + " dias.");
        } catch (NumberFormatException ex) {
            error("Ingresa un numero entero de dias.");
        }
    }

    private void agruparPorCategoria() {
        Map<String, ArrayList<Medicamento>> grupos = controlador.agruparPorCategoria();
        if (grupos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay medicamentos registrados.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        grupos.keySet().stream().sorted().forEach(cat -> {
            sb.append(cat).append(" (").append(grupos.get(cat).size()).append(")\n");
            for (Medicamento m : grupos.get(cat)) {
                sb.append("   - [").append(m.getId()).append("] ").append(m.getNombre())
                  .append("  x").append(m.getCantidad()).append("\n");
            }
            sb.append("\n");
        });
        JTextArea area = new JTextArea(sb.toString(), 18, 40);
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Medicamentos por categoria",
                JOptionPane.INFORMATION_MESSAGE);
    }


    private void mostrarGraficos() {
        if (controlador.getMedicamentos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay medicamentos para graficar.");
            return;
        }

        DefaultCategoryDataset barras = new DefaultCategoryDataset();
        Map<String, ArrayList<Medicamento>> grupos = controlador.agruparPorCategoria();
        grupos.keySet().stream().sorted().forEach(cat -> {
            int total = 0;
            for (Medicamento m : grupos.get(cat)) {
                total += m.getCantidad();
            }
            barras.addValue(total, "Unidades", cat);
        });
        JFreeChart graficoBarras = ChartFactory.createBarChart(
                "Stock por categoria", "Categoria", "Unidades", barras,
                PlotOrientation.VERTICAL, false, true, false);

        int vencidos = 0, porVencer = 0, ok = 0;
        for (Medicamento m : controlador.getMedicamentos()) {
            if (m.estaVencido()) {
                vencidos++;
            } else if (m.venceEn(DIAS_ALERTA)) {
                porVencer++;
            } else {
                ok++;
            }
        }
        DefaultPieDataset<String> torta = new DefaultPieDataset<>();
        torta.setValue("OK", ok);
        torta.setValue("Por vencer (" + DIAS_ALERTA + " dias)", porVencer);
        torta.setValue("Vencidos", vencidos);
        JFreeChart graficoTorta = ChartFactory.createPieChart("Estado del inventario", torta, true, true, false);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Stock por categoria", new ChartPanel(graficoBarras));
        pestanas.addTab("Estado del inventario", new ChartPanel(graficoTorta));

        JDialog dialogo = new JDialog(this, "Graficos del inventario", true);
        dialogo.add(pestanas);
        dialogo.setSize(700, 480);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    private void importarCsv() {
        JFileChooser fc = selectorCsv();
        if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        if (JOptionPane.showConfirmDialog(this,
                "La importacion reemplazara el inventario actual. Continuar?",
                "Importar CSV", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        File archivo = fc.getSelectedFile();
        List<String> errores = new ArrayList<>();
        try {
            int n = controlador.cargarCSV(archivo.getPath(), errores);
            rutaCsv = archivo.getPath();
            mostrarTodos();
            String msg = n + " medicamento(s) importado(s).";
            if (!errores.isEmpty()) {
                msg += "\n\nLineas omitidas (" + errores.size() + "):\n" + String.join("\n", errores);
                JOptionPane.showMessageDialog(this, msg, "Importacion con advertencias", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, msg, "Importacion completa", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            error("No se pudo leer el archivo: " + ex.getMessage());
        }
    }

    private void exportarCsv() {
        JFileChooser fc = selectorCsv();
        fc.setSelectedFile(new File("medicamentos.csv"));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File archivo = fc.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".csv")) {
            archivo = new File(archivo.getPath() + ".csv");
        }
        try {
            controlador.guardarCSV(archivo.getPath());
            rutaCsv = archivo.getPath();
            estado("Exportado a " + archivo.getPath());
        } catch (IOException ex) {
            error("No se pudo guardar el archivo: " + ex.getMessage());
        }
    }

    private void guardar() {
        try {
            controlador.guardarCSV(rutaCsv);
            estado("Guardado en " + rutaCsv);
        } catch (IOException ex) {
            error("No se pudo guardar: " + ex.getMessage());
        }
    }

    private void salir() {
        try {
            controlador.guardarCSV(rutaCsv); // guardado automatico al cerrar
        } catch (IOException ex) {
            if (JOptionPane.showConfirmDialog(this,
                    "No se pudo guardar (" + ex.getMessage() + "). Salir de todos modos?",
                    "Error al guardar", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                return;
            }
        }
        dispose();
        System.exit(0);
    }

    private JFileChooser selectorCsv() {
        JFileChooser fc = new JFileChooser(new File("."));
        fc.setFileFilter(new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv"));
        return fc;
    }


    /*
    * Usa la fila seleccionada; si no hay, pide el ID. Devuelve null si se cancela o es invalido.
    */
    private Integer idSeleccionadoOPreguntar() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            return (Integer) tabla.getValueAt(fila, 0);
        }
        String texto = JOptionPane.showInputDialog(this, "ID del medicamento:", "ID", JOptionPane.QUESTION_MESSAGE);
        if (texto == null) {
            return null;
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException ex) {
            error("El ID debe ser un numero entero.");
            return null;
        }
    }

    private void error(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel barraEstado;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnGraficos;
    private javax.swing.JButton btnImportar;
    private javax.swing.JButton btnPorCategoria;
    private javax.swing.JButton btnPorVencer;
    private javax.swing.JButton btnVender;
    private javax.swing.JButton btnVerTodos;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuMedicamentos;
    private javax.swing.JMenu menuReportes;
    private javax.swing.JMenuItem miAgregar;
    private javax.swing.JMenuItem miBuscar;
    private javax.swing.JMenuItem miCategoria;
    private javax.swing.JMenuItem miEliminar;
    private javax.swing.JMenuItem miExportar;
    private javax.swing.JMenuItem miGraficos;
    private javax.swing.JMenuItem miGuardar;
    private javax.swing.JMenuItem miImportar;
    private javax.swing.JMenuItem miProximos;
    private javax.swing.JMenuItem miSalir;
    private javax.swing.JMenuItem miVender;
    private javax.swing.JMenuItem miVerTodos;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JPopupMenu.Separator separadorArchivo;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
