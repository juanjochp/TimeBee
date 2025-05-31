package es.timebee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * {@code NominaMetadataDto} es un Data Transfer Object (DTO)
 * que contiene la información básica (metadatos) de una nómina
 * en el sistema TimeBee.
 * <p>
 * Este objeto transporta solo los datos esenciales de la nómina,
 * como su identificador y el periodo al que corresponde, sin incluir
 * el contenido del archivo PDF.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NominaMetadataDto {

    /** El identificador único de la nómina. */
    private Long id;

    /** El periodo al que corresponde la nómina (p.ej. abril 2025). */
    private LocalDate periodo;
}
