/**
 * Excepcion lanzada cuando se intenta vender un medicamento
 * cuya fecha de vencimiento ya paso.
 */
public class MedicamentoVencidoException extends Exception {
    public MedicamentoVencidoException(String mensaje) {
        super(mensaje);
    }
}
