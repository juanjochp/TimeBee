package es.timebee.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import es.timebee.exception.ProcesoException;

/**
 * {@code Rol} es un enumerado que representa los diferentes tipos de roles de usuario
 * disponibles en el sistema TimeBee.
 * <p>
 * Los roles determinan qué tipo de acceso y funcionalidades tiene cada usuario en la plataforma.
 * Las opciones disponibles son:
 * <ul>
 *     <li>{@code ADMIN}: usuario con permisos administrativos completos (gestión total).</li>
 *     <li>{@code EMPRESA}: usuario que representa a una empresa y puede gestionar trabajadores, nóminas y permisos.</li>
 *     <li>{@code TRABAJADOR}: usuario que representa a un trabajador individual, con acceso a sus propios datos.</li>
 * </ul>
 * Este enum permite manejar la seguridad y las autorizaciones de forma estructurada en el sistema.
 */
public enum Rol {

    /** Rol administrativo con acceso completo al sistema. */
    ADMIN(0,"ADMIN"),

    /** Rol de empresa, con permisos para gestionar su propia organización y trabajadores. */
    EMPRESA(1,"EMPRESA"),

    /** Rol de trabajador, con acceso solo a su propia información y funcionalidades. */
    TRABAJADOR(2,"TRABAJADOR");

    /** Identificador numérico del rol. */
    private final int id;

    /** Nombre descriptivo del rol. */
    private final String nombre;

    /**
     * Constructor del enum.
     *
     * @param id identificador numérico.
     * @param nombre nombre descriptivo del rol.
     */
    Rol(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Permite convertir una cadena de texto a su correspondiente valor de enum,
     * ignorando mayúsculas/minúsculas.
     *
     * @param value el texto a buscar.
     * @return el {@code Rol} correspondiente.
     * @throws ProcesoException si no se encuentra coincidencia.
     */
    @JsonCreator
    public static Rol fromString(String value) {
        for (Rol tipo : Rol.values()) {
            if (tipo.name().equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new ProcesoException("No se ha encontrado el rol para el valor: "+value);
    }

    /**
     * Define cómo se serializa este enum a JSON.
     *
     * @return el nombre del enum como texto.
     */
    @JsonValue
    public String toJson() { return this.name(); }
}
