package es.timebee.domain.mapper;

import es.timebee.domain.dto.EmpresaDto;
import es.timebee.domain.entity.Empresa;
import org.mapstruct.*;

/**
 * {@code EmpresaMapper} es una interfaz MapStruct que define las conversiones automáticas
 * entre la entidad {@link Empresa} y su correspondiente DTO {@link EmpresaDto}.
 * <p>
 * Gracias a MapStruct, podemos generar implementaciones eficientes de estos mapeos,
 * evitando tener que escribir manualmente el código repetitivo de conversión.
 * <p>
 * Este mapper se integra con Spring (gracias a {@code componentModel = SPRING})
 * y permite tres operaciones principales:
 * <ul>
 *     <li>{@code toEntity}: convierte un DTO a entidad.</li>
 *     <li>{@code toDto}: convierte una entidad a DTO.</li>
 *     <li>{@code partialUpdate}: actualiza parcialmente una entidad usando un DTO,
 *         ignorando propiedades nulas (ideal para operaciones PATCH).</li>
 * </ul>
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmpresaMapper {

    /**
     * Convierte un {@link EmpresaDto} a una entidad {@link Empresa}.
     *
     * @param empresaDto el DTO de empresa.
     * @return la entidad equivalente.
     */
    Empresa toEntity(EmpresaDto empresaDto);

    /**
     * Convierte una entidad {@link Empresa} a su correspondiente {@link EmpresaDto}.
     *
     * @param empresa la entidad de empresa.
     * @return el DTO equivalente.
     */
    EmpresaDto toDto(Empresa empresa);

    /**
     * Actualiza parcialmente una entidad {@link Empresa} con los datos de un {@link EmpresaDto},
     * ignorando las propiedades que sean {@code null} en el DTO.
     *
     * @param empresaDto el DTO con los datos a actualizar.
     * @param empresa la entidad destino a actualizar.
     * @return la entidad actualizada parcialmente.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Empresa partialUpdate(EmpresaDto empresaDto, @MappingTarget Empresa empresa);
}
