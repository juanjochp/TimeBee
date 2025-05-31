package es.timebee.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import es.timebee.exception.ProcesoException;
import lombok.Getter;

/**
 * {@code Genero} es un enumerado que representa las opciones de género disponibles
 * para los trabajadores dentro del sistema TimeBee.
 * <p>
 * Las opciones disponibles son:
 * <ul>
 *     <li>{@code FEMENINO}: género femenino.</li>
 *     <li>{@code MASCULINO}: género masculino.</li>
 *     <li>{@code NO_DETERMINADO}: opción para quienes prefieren no especificar su género.</li>
 * </ul>
 * Este enum ayuda a manejar de forma consistente la información personal de los usuarios.
 */
@Getter
public enum Genero {
    /** Género femenino. */
    FEMENINO(0,"FEMENINO"),

    /** Género masculino. */
    MASCULINO(1,"MASCULINO"),

    /** Género no determinado o no especificado. */
    NO_DETERMINADO(2,"NO DETERMINADO");

    /** Identificador numérico interno del género. */
    private final int id;

    /** Nombre descriptivo del género. */
    private final String nombre;

    /**
     * Constructor del enum.
     *
     * @param id identificador numérico.
     * @param nombre nombre descriptivo.
     */
    Genero(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Permite convertir una cadena de texto a su correspondiente valor de enum,
     * ignorando mayúsculas/minúsculas.
     *
     * @param value el texto a buscar.
     * @return el {@code Genero} correspondiente.
     * @throws ProcesoException si no se encuentra coincidencia.
     */
    @JsonCreator
    public static Genero fromString(String value) {
        for (Genero tipo : Genero.values()) {
            if (tipo.name().equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new ProcesoException("No se ha encontrado el género para el valor: "+value);
    }

    /**
     * Define cómo se serializa este enum a JSON.
     *
     * @return el nombre del enum como texto.
     */
    @JsonValue
    public String toJson() { return this.name(); }
}
