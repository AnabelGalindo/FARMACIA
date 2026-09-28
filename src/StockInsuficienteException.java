/**
 * Excepcion lanzada cuando se intenta vender mas unidades de las
 * que hay disponibles en el inventario.
 */
public class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
