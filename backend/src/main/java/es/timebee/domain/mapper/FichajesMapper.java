package es.timebee.domain.mapper;

import es.timebee.domain.dto.FichajesDto;
import es.timebee.domain.entity.Fichaje;
import org.mapstruct.*;

/**
 * {@code FichajesMapper} es una interfaz MapStruct que define las conversiones automáticas
 * entre la entidad {@link Fichaje} y su correspondiente DTO {@link FichajesDto}.
 * <p>
 * Gracias a MapStruct, podemos generar automáticamente las clases necesarias para
 * convertir datos entre capas, reduciendo errores y evitando escribir código repetitivo.
 * <p>
 * Este mapper usa el componente de Spring (gracias a {@code componentModel = SPRING})
 * y ofrece tres operaciones clave:
 * <ul>
 *     <li>{@code toEntity}: convierte un DTO a entidad.</li>
 *     <li>{@code toDto}: convierte una entidad a DTO.</li>
 *     <li>{@code partialUpdate}: actualiza parcialmente una entidad usando un DTO,
 *         ignorando las propiedades nulas (muy útil para actualizaciones parciales).</li>
 * </ul>
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FichajesMapper {

    /**
     * Convierte un {@link FichajesDto} a una entidad {@link Fichaje}.
     *
     * @param fichajesDto el DTO de fichaje.
     * @return la entidad equivalente.
     */
    Fichaje toEntity(FichajesDto fichajesDto);

    /**
     * Convierte una entidad {@link Fichaje} a su correspondiente {@link FichajesDto}.
     *
     * @param fichaje la entidad de fichaje.
     * @return el DTO equivalente.
     */
    FichajesDto toDto(Fichaje fichaje);

    /**
     * Actualiza parcialmente una entidad {@link Fichaje} con los datos de un {@link FichajesDto},
     * ignorando las propiedades que sean {@code null} en el DTO.
     *
     * @param fichajesDto el DTO con los datos nuevos.
     * @param fichaje la entidad destino que se actualizará.
     * @return la entidad parcialmente actualizada.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Fichaje partialUpdate(FichajesDto fichajesDto, @MappingTarget Fichaje fichaje);
}
