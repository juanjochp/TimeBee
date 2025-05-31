package es.timebee.domain.dto;

import es.timebee.domain.enumeration.FormaJuridica;
import es.timebee.domain.enumeration.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * {@code EmpresaDto} es un Data Transfer Object (DTO) que representa
 * los datos básicos de una empresa dentro del sistema TimeBee.
 * <p>
 * Este objeto se usa para transportar información entre capas,
 * como al listar empresas, consultarlas o exponer datos al frontend.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class EmpresaDto implements Serializable {

    private static final long serialVersionUID = 7956847266873324068L;

    /** El identificador único de la empresa. */
    private Long id;

    /** El nombre de la empresa. */
    private String nombre;

    /** La forma jurídica de la empresa (enum {@code FormaJuridica}). */
    private FormaJuridica formaJuridica;

    /** El número de identificación fiscal (CIF) de la empresa. */
    private String cif;

    /** La dirección física de la empresa. */
    private String direccion;

    /** El número de teléfono de contacto de la empresa. */
    private int telefono;

    /** El correo electrónico principal de la empresa. */
    private String email;

    /** La contraseña encriptada de acceso de la empresa. */
    private String password;

    /** El rol asignado a la empresa dentro del sistema (enum {@code Rol}). */
    private Rol rol;

    /** Indica si la empresa está activa (true) o desactivada (false). */
    private boolean activo;

    /**
     * Constructor vacío requerido por frameworks de serialización/deserialización.
     */
    public EmpresaDto() {
        //Constructor
    }

}
