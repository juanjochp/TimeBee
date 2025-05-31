package es.timebee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code FormaJuridicaDto} es un Data Transfer Object (DTO)
 * que representa los detalles de una forma jurídica dentro del sistema TimeBee.
 * <p>
 * Este objeto permite transportar información sobre las diferentes
 * formas legales que puede adoptar una empresa, como sociedad limitada,
 * autónomo, cooperativa, entre otras.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormaJuridicaDto {

    /** El identificador único de la forma jurídica. */
    private int id;

    /** El nombre descriptivo de la forma jurídica (p.ej. “Sociedad Limitada”). */
    private String nombre;

    /** El código interno asociado (normalmente el {@code name()} del enum). */
    private String codigo;
}
