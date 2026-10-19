/**
 * Excepcion lanzada cuando se busca un medicamento por id o nombre
 * y este no existe en el inventario.
 */
public class MedicamentoNoEncontradoException extends Exception {
    public MedicamentoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
