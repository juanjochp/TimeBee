package es.timebee.exception;

/**
 * {@code ProcesoException} es una excepción personalizada que extiende de {@link RuntimeException}.
 * <p>
 * Esta clase se utiliza para representar errores específicos dentro de los procesos
 * de la aplicación TimeBee, como por ejemplo cuando no se encuentra un valor esperado
 * en un {@code enum} o cuando ocurre algún fallo lógico interno.
 * <p>
 * Permite lanzar mensajes personalizados y, si es necesario, encadenar otras excepciones
 * para mantener la trazabilidad completa de los errores.
 */
public class ProcesoException extends RuntimeException {

    private static final long serialVersionUID = 2644977082647207774L;

    /**
     * Crea una nueva {@code ProcesoException} solo con un mensaje descriptivo.
     *
     * @param message el mensaje de error
     */
    public ProcesoException(String message) {
        super(message);
    }

    /**
     * Crea una nueva {@code ProcesoException} con un mensaje y una causa subyacente.
     *
     * @param mensaje el mensaje de error
     * @param causa   la excepción original que causó este error
     */
    public ProcesoException(String mensaje, Throwable causa) { super(mensaje,causa); }

    /**
     * Crea una nueva {@code ProcesoException} solo con una causa subyacente.
     *
     * @param causa la excepción original que causó este error
     */
    public  ProcesoException(Throwable causa) { super(causa); }
}
