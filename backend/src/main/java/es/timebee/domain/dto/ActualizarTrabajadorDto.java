package es.timebee.domain.dto;

import es.timebee.domain.enumeration.Genero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * {@code ActualizarTrabajadorDto} es un Data Transfer Object (DTO)
 * que encapsula la información necesaria para actualizar los datos
 * de un trabajador en el sistema TimeBee.
 * <p>
 * Este objeto es enviado desde el cliente cuando un trabajador desea
 * modificar sus datos personales, incluyendo su nombre, apellidos, género,
 * DNI, cuenta bancaria, fecha de nacimiento, teléfono, email y, si aplica,
 * su contraseña.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ActualizarTrabajadorDto implements Serializable {

    private static final long serialVersionUID = -327855632753575638L;

    /** El nombre actualizado del trabajador. */
    private String nombre;

    /** Los apellidos actualizados del trabajador. */
    private String apellidos;

    /** El género del trabajador (usando el enum {@code Genero}). */
    private Genero genero;

    /** El DNI actualizado del trabajador. */
    private String dni;

    /** El IBAN actualizado de la cuenta bancaria del trabajador. */
    private String iban;

    /** La fecha de nacimiento actualizada del trabajador. */
    private LocalDate fechaNacimiento;

    /** El número de teléfono actualizado del trabajador. */
    private int telefono;

    /** El correo electrónico actualizado del trabajador. */
    private String email;

    /** La contraseña antigua (necesaria para validar el cambio de clave). */
    private String passwordAntigua;

    /** La nueva contraseña que el trabajador desea establecer. */
    private String passwordNueva;

    /**
     * Constructor vacío requerido por frameworks de serialización/deserialización.
     */
    public ActualizarTrabajadorDto() {
        //Constructor
    }
}
