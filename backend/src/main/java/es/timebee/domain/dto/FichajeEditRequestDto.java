package es.timebee.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * {@code FichajeEditRequestDto} es un Data Transfer Object (DTO)
 * que encapsula la información necesaria para editar un fichaje
 * en el sistema TimeBee.
 * <p>
 * Este objeto es enviado desde el cliente cuando se necesita actualizar
 * las marcas de entrada o salida de un trabajador, permitiendo modificar
 * tanto la fecha de inicio como la de fin del fichaje.
 */
@Data
public class FichajeEditRequestDto {

    /** El identificador único del fichaje a editar (no puede ser nulo). */
    @NotNull(message = "El id del fichaje es obligatorio")
    private Long id;

    /** El identificador único del trabajador asociado al fichaje (no puede ser nulo). */
    @NotNull(message = "El id del trabajador es obligatorio")
    private Long trabajadorId;

    /** El identificador único de la empresa asociada al fichaje (no puede ser nulo). */
    @NotNull(message = "El id de la empresa es obligatorio")
    private Long empresaId;

    /**
     * La fecha y hora de inicio del fichaje (en formato ISO: yyyy-MM-dd'T'HH:mm:ss).
     * Este campo es obligatorio.
     */
    @NotNull(message = "La fecha de inicio es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaInicio;

    /**
     * La fecha y hora de fin del fichaje (en formato ISO: yyyy-MM-dd'T'HH:mm:ss).
     * Este campo es opcional.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaFin;
}
