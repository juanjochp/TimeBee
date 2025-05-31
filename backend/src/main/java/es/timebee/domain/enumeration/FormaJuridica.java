package es.timebee.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import es.timebee.exception.ProcesoException;
import lombok.Getter;

/**
 * {@code FormaJuridica} es un enumerado que representa los distintos tipos
 * de formas jurídicas que puede tener una empresa registrada en el sistema TimeBee.
 * <p>
 * Las opciones disponibles incluyen:
 * <ul>
 *     <li>{@code AUTONOMO}: trabajador por cuenta propia.</li>
 *     <li>{@code SOCIEDAD_LIMITADA}: empresa constituida como S.L.</li>
 *     <li>{@code SOCIEDAD_ANONIMA}: empresa constituida como S.A.</li>
 *     <li>{@code COOPERATIVA}: empresa bajo modelo de cooperativa.</li>
 * </ul>
 * Este enum facilita la clasificación jurídica de las empresas dentro del sistema
 * y también maneja la conversión y serialización de datos.
 */
@Getter
public enum FormaJuridica {

    /** Empresa registrada como trabajador autónomo. */
    AUTONOMO(0,"AUTONOMO"),

    /** Empresa constituida como sociedad limitada (S.L.). */
    SOCIEDAD_LIMITADA(1,"SOCIEDAD LIMITADA"),

    /** Empresa constituida como sociedad anónima (S.A.). */
    SOCIEDAD_ANONIMA(2,"SOCIEDAD ANONIMA"),

    /** Empresa bajo modelo de cooperativa. */
    COOPERATIVA(3,"COOPERATIVA");

    /** Identificador numérico interno de la forma jurídica. */
    private final int id;

    /** Nombre descriptivo de la forma jurídica. */
    private final String nombre;

    /**
     * Constructor del enum.
     *
     * @param id identificador numérico.
     * @param nombre nombre descriptivo.
     */
    FormaJuridica(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Permite convertir una cadena de texto a su correspondiente valor de enum,
     * ignorando mayúsculas/minúsculas.
     *
     * @param value el texto a buscar.
     * @return el {@code FormaJuridica} correspondiente.
     * @throws ProcesoException si no se encuentra coincidencia.
     */
    @JsonCreator
    public static FormaJuridica fromString(String value) {
        for (FormaJuridica tipo: FormaJuridica.values()) {
            if (tipo.name().equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new ProcesoException("No se ha encontrado la forma jurídica para el valor: " + value);
    }

    /**
     * Define cómo se serializa este enum a JSON.
     *
     * @return el nombre del enum como texto.
     */
    @JsonValue
    public String toJson() { return this.name(); }
}
