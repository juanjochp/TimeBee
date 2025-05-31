package es.timebee.domain.mapper;

import es.timebee.domain.dto.TrabajadorDto;
import es.timebee.domain.entity.Empresa;
import es.timebee.domain.entity.Trabajador;
import org.mapstruct.*;

/**
 * {@code TrabajadorMapper} es una interfaz de MapStruct que se encarga de transformar
 * datos entre la entidad {@link Trabajador} y su DTO correspondiente {@link TrabajadorDto}.
 * <p>
 * Esta conversión automática permite que los datos fluyan correctamente entre las capas
 * de la aplicación, evitando tener que escribir código manual repetitivo y propenso a errores.
 * <p>
 * Integra tres operaciones principales, con el añadido especial de manejar el mapeo del {@code empresaId}:
 * <ul>
 *     <li>{@code toEntity}: convierte un DTO en entidad, mapeando el {@code empresaId} a un objeto {@link Empresa}.</li>
 *     <li>{@code toDto}: convierte una entidad en DTO, extrayendo el {@code empresa.id}.</li>
 *     <li>{@code partialUpdate}: actualiza parcialmente una entidad usando un DTO, ignorando los campos nulos.</li>
 * </ul>
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrabajadorMapper {

    /**
     * Convierte un {@link TrabajadorDto} en una entidad {@link Trabajador},
     * mapeando específicamente el campo {@code empresa_id} a un objeto {@link Empresa}.
     *
     * @param trabajadorDto el DTO con los datos del trabajador.
     * @return la entidad {@code Trabajador} resultante.
     */
    @Mapping(target = "empresa", expression = "java(convertEmpresaIdToEmpresa(trabajadorDto.getEmpresa_id()))")
    Trabajador toEntity(TrabajadorDto trabajadorDto);

    /**
     * Convierte una entidad {@link Trabajador} en su correspondiente {@link TrabajadorDto},
     * extrayendo el {@code id} de la empresa asociada.
     *
     * @param trabajador la entidad con los datos del trabajador.
     * @return el DTO equivalente.
     */
    @Mapping(target = "empresa_id", source = "empresa.id")
    TrabajadorDto toDto(Trabajador trabajador);

    /**
     * Método de ayuda que convierte un {@code empresaId} (Long) en un objeto {@link Empresa},
     * solo estableciendo el campo {@code id}.
     *
     * @param empresaId el identificador de la empresa.
     * @return un objeto {@code Empresa} con solo el id seteado, o {@code null} si no se pasa id.
     */
    default Empresa convertEmpresaIdToEmpresa(Long empresaId) {
        if (empresaId == null) {
            return null;
        }
        Empresa empresa = new Empresa();
        empresa.setId(empresaId);
        return empresa;
    }

    /**
     * Actualiza parcialmente una entidad {@link Trabajador} usando los datos de un {@link TrabajadorDto},
     * ignorando cualquier campo que sea {@code null} en el DTO para evitar sobrescribir valores no proporcionados.
     *
     * @param trabajadorDto el DTO con los datos a aplicar.
     * @param trabajador la entidad destino a actualizar.
     * @return la entidad {@code Trabajador} parcialmente actualizada.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Trabajador partialUpdate(TrabajadorDto trabajadorDto, @MappingTarget Trabajador trabajador);
}
