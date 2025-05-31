package es.timebee.utils;

import es.timebee.domain.dto.ApiRespuesta;

/**
 * Utilidad para generar respuestas API consistentes.
 * <p>
 * Esta clase ofrece métodos estáticos que permiten construir
 * respuestas exitosas o de error de manera uniforme,
 * garantizando que el cliente siempre reciba datos
 * bien estructurados y fáciles de interpretar.
 */
public class ResponseUtils {

    /**
     * Constructor.
     */
    public ResponseUtils() {
        //Constructor
    }

    /**
     * Genera una respuesta API de éxito.
     * <p>
     * Recibe cualquier tipo de dato y lo envuelve en una respuesta estándar,
     * marcando el estado como EXITO.
     *
     * @param data datos a incluir en la respuesta
     * @param <T> tipo de los datos
     * @return respuesta API construida con estado EXITO
     */
    public static <T> ApiRespuesta<T> generarRespuesta(T data) {
        return ApiRespuesta.<T>builder()
                .estado(ApiRespuesta.Estado.EXITO)
                .data(data)
                .build();

    }

    /**
     * Genera una respuesta API de error con solo un mensaje.
     * <p>
     * Útil cuando queremos devolver simplemente
     * un texto explicativo del error ocurrido.
     *
     * @param mensaje mensaje de error a incluir
     * @return respuesta API construida con estado ERROR
     */
    public static ApiRespuesta<String> generarRespuestaError(String mensaje) {
        return ApiRespuesta.<String>builder()
                .estado(ApiRespuesta.Estado.ERROR)
                .data(mensaje)
                .build();
    }
}
