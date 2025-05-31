package es.timebee.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * {@code ApiRespuesta} es un contenedor genérico que estandariza las respuestas
 * enviadas por la API de TimeBee.
 * <p>
 * Este DTO envuelve cualquier tipo de dato retornado en los endpoints,
 * junto con un estado que indica si la operación fue exitosa o si ocurrió un error.
 * Gracias a esta estructura, los clientes pueden interpretar las respuestas
 * de forma consistente y clara.
 *
 * @param <T> el tipo de dato que contiene la respuesta (puede ser cualquier objeto)
 */
@Data
@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@Schema(description = "Respuestas estandarizadas de la api")
public final class ApiRespuesta<T> {

    /** El dato principal retornado por la API (si la operación fue exitosa). */
    private T data;

    /** El estado de la respuesta: éxito o error. */
    private Estado estado;

    /**
     * {@code Estado} es un enumerado que indica si la respuesta fue un éxito
     * o si hubo un error.
     * <p>
     * Se serializa como cadena (“exito” o “error”) para que los clientes
     * puedan interpretarlo fácilmente al recibir las respuestas JSON.
     */
    public enum Estado {
        /** Indica que la operación fue exitosa. */
        EXITO("exito"),

        /** Indica que ocurrió un error en la operación. */
        ERROR("error");

        private final String valor;

        Estado(String estado) {
            this.valor = estado;
        }

        /**
         * Devuelve el valor como cadena que será usado en la serialización JSON.
         *
         * @return el valor del estado como texto (“exito” o “error”)
         */
        @JsonValue
        public String getValue() {return valor;}
    }

}
