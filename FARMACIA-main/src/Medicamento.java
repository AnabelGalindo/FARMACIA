import java.time.LocalDate;

/**
 * Clase que representa un medicamento dentro del inventario de la farmacia.
 */
public class Medicamento {
    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int cantidad;
    private LocalDate fechaVencimiento;

    public Medicamento(int id, String nombre, String categoria, double precio, int cantidad,
                        LocalDate fechaVencimiento) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Indica si el medicamento ya superó su fecha de vencimiento.
     */
    public boolean estaVencido() {
        return fechaVencimiento.isBefore(LocalDate.now());
    }

    /**
     * Indica si el medicamento vence dentro de los proximos "dias" indicados.
     */
    public boolean venceEn(int dias) {
        LocalDate limite = LocalDate.now().plusDays(dias);
        return !fechaVencimiento.isBefore(LocalDate.now()) && !fechaVencimiento.isAfter(limite);
    }

    @Override
    public String toString() {
        String estado = estaVencido() ? "Vencido" : "Vigente";
        return String.format("[%d] %-15s | %-12s | S/ %6.2f | Stock: %3d | Vence: %s (%s)",
                id, nombre, categoria, precio, cantidad, fechaVencimiento, estado);
    }
}
