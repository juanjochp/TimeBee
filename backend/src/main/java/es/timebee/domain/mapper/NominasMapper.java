package es.timebee.domain.mapper;

import es.timebee.domain.dto.NominasDto;
import es.timebee.domain.entity.Nomina;
import org.mapstruct.*;

/**
 * {@code NominasMapper} es una interfaz de MapStruct que define los mapeos automáticos
 * entre la entidad {@link Nomina} y su correspondiente DTO {@link NominasDto}.
 * <p>
 * Gracias a MapStruct, evitamos escribir manualmente el código repetitivo de conversión
 * entre objetos, mejorando la eficiencia y reduciendo errores humanos.
 * <p>
 * Este mapper se integra con el ecosistema Spring (mediante {@code componentModel = SPRING})
 * y proporciona tres operaciones principales:
 * <ul>
 *     <li>{@code toEntity}: convierte un DTO a entidad.</li>
 *     <li>{@code toDto}: convierte una entidad a DTO.</li>
 *     <li>{@code partialUpdate}: actualiza parcialmente una entidad usando un DTO,
 *         ignorando los campos nulos (ideal para actualizaciones parciales o PATCH).</li>
 * </ul>
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NominasMapper {

    /**
     * Convierte un {@link NominasDto} en una entidad {@link Nomina}.
     *
     * @param nominasDto el DTO que contiene los datos.
     * @return la entidad {@code Nomina} equivalente.
     */
    Nomina toEntity(NominasDto nominasDto);

    /**
     * Convierte una entidad {@link Nomina} en su correspondiente {@link NominasDto}.
     *
     * @param nomina la entidad con los datos de nómina.
     * @return el DTO equivalente.
     */
    NominasDto toDto(Nomina nomina);

    /**
     * Actualiza parcialmente una entidad {@link Nomina} con los valores de un {@link NominasDto},
     * ignorando cualquier campo {@code null} en el DTO para evitar sobrescribir datos no proporcionados.
     *
     * @param nominasDto el DTO con los datos a aplicar.
     * @param nomina la entidad destino que se actualizará.
     * @return la entidad actualizada parcialmente.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Nomina partialUpdate(NominasDto nominasDto, @MappingTarget Nomina nomina);
}
