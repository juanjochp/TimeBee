package es.timebee.domain.dto;
import es.timebee.domain.enumeration.Genero;
import es.timebee.domain.enumeration.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * {@code TrabajadorDto} es un Data Transfer Object (DTO)
 * que representa la información completa de un trabajador
 * en el sistema TimeBee.
 * <p>
 * Este objeto transporta datos personales, laborales y de acceso,
 * permitiendo exponerlos al frontend o entre capas de la aplicación
 * para operaciones como visualización, edición o gestión de usuarios.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TrabajadorDto implements Serializable {

    private static final long serialVersionUID = 6920705739204938737L;

    /** El identificador único del trabajador. */
    private Long id;

    /** El nombre del trabajador. */
    private String nombre;

    /** Los apellidos del trabajador. */
    private String apellidos;

    /** El número de DNI del trabajador. */
    private String  dni;

    /** El número NAF (Número de Afiliación a la Seguridad Social) del trabajador. */
    private String naf;

    /** El género del trabajador (enum {@code Genero}). */
    private Genero genero;

    /** La fecha de nacimiento del trabajador. */
    private LocalDate fechaNacimiento;

    /** La fecha de antigüedad laboral del trabajador en la empresa. */
    private LocalDate fechaAntiguedad;

    /** La categoría laboral asignada al trabajador. */
    private String categoria;

    /** El número IBAN de la cuenta bancaria del trabajador. */
    private String iban;

    /** El número de teléfono del trabajador. */
    private int telefono;

    /** El correo electrónico del trabajador. */
    private String email;

    /** La contraseña encriptada del trabajador. */
    private String password;

    /** El rol del trabajador dentro del sistema (enum {@code Rol}). */
    private Rol rol;

    /** Indica si el trabajador está activo (true) o inactivo (false). */
    private boolean activo;

    /** El identificador único de la empresa a la que pertenece el trabajador. */
    private Long empresa_id;

    /**
     * Constructor vacío requerido por frameworks de serialización/deserialización.
     */
    public TrabajadorDto() {
        //Constructor
    }
}
