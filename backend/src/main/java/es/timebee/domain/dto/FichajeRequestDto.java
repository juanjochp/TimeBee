package es.timebee.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * {@code FichajeRequestDto} es un Data Transfer Object (DTO)
 * que encapsula la información necesaria para crear un nuevo fichaje
 * en el sistema TimeBee.
 * <p>
 * Este objeto se utiliza cuando un trabajador registra su entrada o salida,
 * enviando los datos esenciales como el trabajador, la empresa, y las marcas
 * de tiempo correspondientes.
 */
@Data
public class FichajeRequestDto {

    /** El identificador único del trabajador que realiza el fichaje (no puede ser nulo). */
    @NotNull(message = "El id del trabajador es obligatorio")
    private Long trabajadorId;

    /** El identificador único de la empresa donde se realiza el fichaje (no puede ser nulo). */
    @NotNull(message = "El id de la empresa es obligatorio")
    private Long empresaId;

    /**
     * La fecha y hora de inicio del fichaje (en formato ISO: yyyy-MM-dd'T'HH:mm:ss).
     * Este campo es obligatorio, ya que marca el momento exacto de la entrada o salida.
     */
    @NotNull(message = "La fecha de inicio es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaInicio;

    /**
     * La fecha y hora de fin del fichaje (en formato ISO: yyyy-MM-dd'T'HH:mm:ss).
     * Este campo es opcional y se usa cuando el fichaje incluye hora de salida.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaFin;
}
