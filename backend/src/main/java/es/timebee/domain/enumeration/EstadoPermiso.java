package es.timebee.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import es.timebee.exception.ProcesoException;
import lombok.Getter;

/**
 * {@code EstadoPermiso} es un enumerado que representa los diferentes estados
 * que puede tener una solicitud de permiso dentro del sistema TimeBee.
 * <p>
 * Los estados disponibles son:
 * <ul>
 *     <li>{@code SOLICITADO}: el trabajador ha solicitado el permiso, pero aún no ha sido revisado.</li>
 *     <li>{@code APROBADO}: el permiso ha sido revisado y aprobado por la empresa.</li>
 *     <li>{@code RECHAZADO}: la empresa ha denegado la solicitud del permiso.</li>
 * </ul>
 * Este enum también incluye lógica para convertir cadenas en enums y para
 * controlar cómo se serializan en JSON.
 */
@Getter
public enum EstadoPermiso {
    /** Permiso solicitado por el trabajador, pendiente de validación. */
    SOLICITADO(0,"SOLICITADO"),

    /** Permiso que ha sido aprobado por la empresa. */
    APROBADO(1,"APROBADO"),

    /** Permiso que ha sido rechazado por la empresa. */
    RECHAZADO(2,"RECHAZADO");

    /** Identificador numérico interno del estado. */
    private final int id;

    /** Nombre descriptivo del estado. */
    private  final String nombre;

    /**
     * Constructor del enum.
     *
     * @param id identificador numérico.
     * @param nombre nombre descriptivo del estado.
     */
    EstadoPermiso(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Permite convertir una cadena de texto a su correspondiente valor de enum,
     * ignorando mayúsculas/minúsculas.
     *
     * @param value el texto a buscar.
     * @return el {@code EstadoPermiso} correspondiente.
     * @throws ProcesoException si no se encuentra coincidencia.
     */
    @JsonCreator
    public static EstadoPermiso fromString(String value) {
        for (EstadoPermiso estadoPermiso : EstadoPermiso.values()) {
            if (estadoPermiso.name().equalsIgnoreCase(value)) {
                return estadoPermiso;
            }
        }
        throw new ProcesoException("No se ha encontrado el estado del permiso para el valor: "+value);
    }

    /**
     * Define cómo se serializa este enum a JSON.
     *
     * @return el nombre del enum como texto.
     */
    @JsonValue
    public String toJson() { return this.name(); }

}
