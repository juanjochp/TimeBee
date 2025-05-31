package es.timebee.domain.dto;

import es.timebee.domain.enumeration.FormaJuridica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * {@code ActualizarEmpresaDto} es un Data Transfer Object (DTO)
 * que encapsula la información necesaria para actualizar los datos
 * de una empresa en el sistema TimeBee.
 * <p>
 * Este objeto es enviado desde el cliente cuando se desea modificar
 * los datos de la empresa, incluyendo su nombre, forma jurídica, CIF,
 * dirección, teléfono, email y contraseñas (antigua y nueva).
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ActualizarEmpresaDto implements Serializable {

    private static final long serialVersionUID = -327855632753575638L;

    /** El nombre actualizado de la empresa. */
    private String nombre;

    /** La forma jurídica seleccionada para la empresa (enum). */
    private FormaJuridica formaJuridica;

    /** El CIF actualizado de la empresa. */
    private String cif;

    /** La dirección física actualizada de la empresa. */
    private String direccion;

    /** El número de teléfono actualizado de la empresa. */
    private int telefono;

    /** El correo electrónico actualizado de la empresa. */
    private String email;

    /** La contraseña antigua (para validar cambios de clave). */
    private String passwordAntigua;

    /** La nueva contraseña que se desea establecer. */
    private String passwordNueva;

    /**
     * Constructor vacío requerido por frameworks y librerías
     * de serialización/deserialización.
     */
    public ActualizarEmpresaDto() {
        //Constructor
    }


}
