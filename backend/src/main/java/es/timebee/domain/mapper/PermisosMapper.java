package es.timebee.domain.mapper;

import es.timebee.domain.dto.PermisosDto;
import es.timebee.domain.entity.Permiso;
import org.mapstruct.*;

/**
 * {@code PermisosMapper} es una interfaz de MapStruct que gestiona la conversión automática
 * entre la entidad {@link Permiso} y su correspondiente DTO {@link PermisosDto}.
 * <p>
 * MapStruct permite generar estas conversiones de manera eficiente y segura,
 * eliminando la necesidad de escribir código manual repetitivo para pasar datos
 * entre las capas de la aplicación.
 * <p>
 * Este mapper está configurado para integrarse con Spring ({@code componentModel = SPRING})
 * y ofrece tres operaciones principales:
 * <ul>
 *     <li>{@code toEntity}: convierte un DTO a entidad.</li>
 *     <li>{@code toDto}: convierte una entidad a DTO.</li>
 *     <li>{@code partialUpdate}: permite actualizar parcialmente una entidad usando un DTO,
 *         ignorando los campos que sean {@code null} (muy útil para actualizaciones parciales).</li>
 * </ul>
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermisosMapper {

    /**
     * Convierte un {@link PermisosDto} en una entidad {@link Permiso}.
     *
     * @param permisosDto el DTO que contiene los datos del permiso.
     * @return la entidad {@code Permiso} correspondiente.
     */
    Permiso toEntity(PermisosDto permisosDto);

    /**
     * Convierte una entidad {@link Permiso} en su correspondiente {@link PermisosDto}.
     *
     * @param permiso la entidad con los datos.
     * @return el DTO equivalente.
     */
    PermisosDto toDto(Permiso permiso);

    /**
     * Actualiza parcialmente una entidad {@link Permiso} utilizando los valores de un {@link PermisosDto},
     * omitiendo cualquier campo que sea {@code null} en el DTO para no sobrescribir valores no proporcionados.
     *
     * @param permisosDto el DTO con los datos a aplicar.
     * @param permiso la entidad destino a actualizar.
     * @return la entidad actualizada parcialmente.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Permiso partialUpdate(PermisosDto permisosDto, @MappingTarget Permiso permiso);
}
