package es.timebee.services;

import es.timebee.domain.dto.NominaDownloadDto;
import es.timebee.domain.dto.NominaMetadataDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio encargado de gestionar las nóminas.
 * <p>
 * Desde subir documentos PDF hasta listarlos, descargarlos o eliminarlos,
 * este servicio es el guardián de la información salarial.
 * Un módulo que trabaja con cuidado, garantizando seguridad y trazabilidad.
 */
public interface NominaService {

    /**
     * Sube una nómina (archivo PDF) al sistema.
     * <p>
     * Valida trabajador, empresa y formato del archivo,
     * y guarda todo de forma segura.
     *
     * @param trabajadorId id del trabajador
     * @param periodo mes/año al que pertenece la nómina
     * @param file archivo PDF de la nómina
     * @param emailEmpresa correo de la empresa que sube el archivo
     * @return true si se guarda correctamente
     */
    Boolean subirNomina(@NotNull Long trabajadorId, LocalDate periodo, MultipartFile file, String emailEmpresa);

    /**
     * Elimina una nómina del sistema.
     * <p>
     * Solo empresas autorizadas pueden realizar esta operación.
     *
     * @param id id de la nómina
     * @param emailEmpresa correo de la empresa que solicita la eliminación
     * @return true si se elimina correctamente
     */
    Boolean eliminarNomina(Long id, String emailEmpresa);

    /**
     * Obtiene la lista de nóminas asociadas a un trabajador (por id).
     * <p>
     * Ideal para mostrar historiales, listados o reportes.
     *
     * @param trabajadorId id del trabajador
     * @return lista de metadatos de nóminas
     */
    List<NominaMetadataDto> obtenerNominasPorTrabajador(Long trabajadorId);

    /**
     * Obtiene la lista de nóminas asociadas a un trabajador (por email).
     *
     * @param trabajadorEmail correo del trabajador
     * @return lista de metadatos de nóminas
     */
    List<NominaMetadataDto> obtenerNominasPorTrabajadorEmail(String trabajadorEmail);

    /**
     * Descarga el archivo PDF de una nómina específica.
     * <p>
     * Verifica permisos antes de entregar el documento.
     *
     * @param id id de la nómina
     * @return DTO con el contenido del PDF y sus metadatos
     */
    NominaDownloadDto descargarNomina(Long id);
}
