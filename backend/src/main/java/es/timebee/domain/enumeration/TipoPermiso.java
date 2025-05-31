package es.timebee.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import es.timebee.exception.ProcesoException;
import lombok.Getter;

/**
 * {@code TipoPermiso} es un enumerado que representa los diferentes tipos de permisos laborales
 * que los trabajadores pueden solicitar dentro del sistema TimeBee.
 * <p>
 * Cada tipo cubre una situación concreta, alineada con las necesidades reales de la gestión laboral.
 * Las opciones disponibles incluyen:
 * <ul>
 *     <li>{@code VACACIONES}: días de descanso planificados.</li>
 *     <li>{@code INDISPOSICION_MEDICA}: enfermedad leve que requiere ausencia breve.</li>
 *     <li>{@code ASISTENCIA_MEDICA}: consultas médicas puntuales.</li>
 *     <li>{@code HOSPITALIZACION_FAMILIAR}: cuidado o acompañamiento de un familiar hospitalizado.</li>
 *     <li>{@code INTERVENCION_QUIRURGICA}: operaciones o procedimientos médicos importantes.</li>
 *     <li>{@code ENFERMEDAD}: enfermedad más prolongada o grave.</li>
 *     <li>{@code ACCIDENTE_GRAVE}: situaciones derivadas de accidentes graves.</li>
 *     <li>{@code FALLECIMIENTO_FAMILIAR}: permiso por fallecimiento de un familiar.</li>
 *     <li>{@code MATRIMONIO}: días libres por matrimonio propio.</li>
 *     <li>{@code EXAMEN_PRENATAL}: controles médicos relacionados con el embarazo.</li>
 *     <li>{@code NACIMIENTO_HIJO}: permisos relacionados con el nacimiento de un hijo.</li>
 *     <li>{@code PERMISO_MATERNIDAD}: baja por maternidad.</li>
 *     <li>{@code PERMISO_PATERNIDAD}: baja por paternidad.</li>
 *     <li>{@code LACTANCIA}: permisos de lactancia.</li>
 *     <li>{@code ASISTENCIA_EXAMEN}: permisos para asistir a exámenes.</li>
 *     <li>{@code MUDANZA}: permisos por cambio de domicilio.</li>
 *     <li>{@code BAJA_ACCIDENTE}: baja por accidente laboral.</li>
 *     <li>{@code BAJA_ENFERMEDAD}: baja por enfermedad general.</li>
 * </ul>
 * Este enum permite manejar las solicitudes de forma estructurada y consistente.
 */
@Getter
public enum TipoPermiso {
    VACACIONES(0,"VACACIONES"),
    INDISPOSICION_MEDICA(1,"INDISPOSICION MEDICA"),
    ASISTENCIA_MEDICA(2,"ASISTENCIA MEDICA"),
    HOSPITALIZACION_FAMILIAR(3,"HOSPITALIZACION FAMILIAR"),
    INTERVENCION_QUIRURGICA(4,"INTERVENCION QUIRURGICA"),
    ENFERMEDAD(5,"ENFERMEDAD"),
    ACCIDENTE_GRAVE(6,"ACCIDENTE GRAVE"),
    FALLECIMIENTO_FAMILIAR(7,"FALLECIMIENTO FAMILIAR"),
    MATRIMONIO(8,"MATRIMONIO"),
    EXAMEN_PRENATAL(9,"EXAMEN_PRENATAL"),
    NACIMIENTO_HIJO(10,"NACIMIENTO HIJO"),
    PERMISO_MATERNIDAD(11,"PERMISO POR MATERNIDAD"),
    PERMISO_PATERNIDAD(12,"PERMISO POR PATERNIDAD"),
    LACTANCIA(13,"LACTANCIA"),
    ASISTENCIA_EXAMEN(14,"ASISTENCIA A EXAMEN"),
    MUDANZA(15,"MUDANZA"),
    BAJA_ACCIDENTE(16,"BAJA POR ACCIDENTE LABORAL"),
    BAJA_ENFERMEDAD(17,"BAJA POR ENFERMEDAD");

    /** Identificador numérico interno del tipo de permiso. */
    private final int id;

    /** Nombre descriptivo del tipo de permiso. */
    private final String nombre;

    /**
     * Constructor del enum.
     *
     * @param id identificador numérico.
     * @param nombre nombre descriptivo.
     */
    TipoPermiso(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Permite convertir una cadena de texto a su correspondiente valor de enum,
     * ignorando mayúsculas/minúsculas.
     *
     * @param value el texto a buscar.
     * @return el {@code TipoPermiso} correspondiente.
     * @throws ProcesoException si no se encuentra coincidencia.
     */
    @JsonCreator
    public static TipoPermiso fromString(String value) {
        for (TipoPermiso tipo : TipoPermiso.values()) {
            if (tipo.name().equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new ProcesoException("No se ha encontrado el tipo de permiso para el valor: "+value);
    }

    /**
     * Define cómo se serializa este enum a JSON.
     *
     * @return el nombre del enum como texto.
     */
    @JsonValue
    public String toJson() { return this.name(); }
}


