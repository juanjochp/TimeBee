package es.timebee.resources;

import es.timebee.domain.dto.*;
import es.timebee.security.annotations.*;
import es.timebee.services.NominaService;
import es.timebee.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * {@code NominaResource} es el controlador REST encargado de gestionar
 * todas las operaciones relacionadas con las nóminas en la aplicación TimeBee.
 * <p>
 * Aquí se manejan tareas importantes como subir, eliminar, listar y descargar nóminas.
 * Es un recurso pensado tanto para las empresas (que suben y administran)
 * como para los trabajadores (que descargan y consultan sus documentos).
 * <p>
 * Este controlador garantiza que la experiencia con las nóminas sea fluida y clara,
 * permitiendo que tanto empresas como empleados accedan rápidamente a lo que necesitan.
 */
@RestController
@RequestMapping("/nominas")
@Tag(name = "Nominas", description = "Operaciones relacionadas con las nóminas de empleados")
public class NominaResource {

    private final NominaService nominaService;

    /**
     * Constructor que inyecta el servicio encargado de la lógica de nóminas.
     *
     * @param nominaService servicio de nóminas
     */
    public NominaResource(NominaService nominaService) {
        this.nominaService = nominaService;
    }

    /**
     * Permite a la empresa subir una nómina (en formato PDF) para un trabajador.
     *
     * @param trabajadorId id del trabajador
     * @param auth         información de autenticación
     * @param periodo      periodo al que corresponde la nómina
     * @param file         archivo PDF de la nómina
     * @return respuesta indicando si se subió correctamente
     */
    @PermisoEmpresa
    @Operation(summary = "Subir una nómina", description = "Permite a la empresa subir una nómina en formato PDF para un trabajador")
    @PostMapping(value = "/subir", consumes = "multipart/form-data")
    public ResponseEntity<ApiRespuesta<Boolean>> subirNomina(
            @RequestParam("trabajadorId") @NotNull Long trabajadorId,
            Authentication auth,
            @RequestParam("periodo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodo,
            @RequestParam("file") MultipartFile file) {

        Boolean exito = nominaService.subirNomina(trabajadorId, periodo, file, auth.getName());
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(exito));
    }

    /**
     * Permite a la empresa eliminar una nómina por su id.
     *
     * @param request objeto que contiene el id de la nómina
     * @param auth    información de autenticación
     * @return respuesta indicando si se eliminó correctamente
     */
    @PermisoEmpresa
    @Operation(
            summary = "Eliminar una nómina",
            description = "Permite a la empresa eliminar una nómina de la base de datos mediante su id usando POST"
    )
    @PostMapping("/eliminar")
    public ResponseEntity<ApiRespuesta<Boolean>> eliminarNomina(@RequestBody IdRequestDto request, Authentication auth) {
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(nominaService.eliminarNomina(request.getId(), auth.getName())));
    }

    /**
     * Devuelve una lista de los periodos de nóminas de un trabajador usando su id.
     *
     * @param request objeto que contiene el id del trabajador
     * @return lista con metadatos de nóminas (id y periodo)
     */
    @PermisoEmpresa
    @Operation(
            summary = "Listar períodos de nóminas de un trabajador",
            description = "Devuelve una lista con id y periodo de todas las nóminas subidas para un trabajador"
    )
    @PostMapping("/trabajador")
    public ResponseEntity<ApiRespuesta<List<NominaMetadataDto>>> listarNominasDeTrabajador(
            @RequestBody IdRequestDto request) {
        List<NominaMetadataDto> lista = nominaService.obtenerNominasPorTrabajador(request.getId());
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(lista));
    }

    /**
     * Devuelve una lista de los periodos de nóminas del trabajador autenticado.
     *
     * @param auth información de autenticación
     * @return lista con metadatos de nóminas (id y periodo)
     */
    @PermisoTrabajador
    @Operation(
            summary = "Listar períodos de nóminas de un trabajador",
            description = "Devuelve una lista con id y periodo de todas las nóminas subidas para un trabajador"
    )
    @PostMapping("/trabajador-email")
    public ResponseEntity<ApiRespuesta<List<NominaMetadataDto>>> listarNominasDeTrabajadorPorEmail(
            Authentication auth) {
        List<NominaMetadataDto> lista = nominaService.obtenerNominasPorTrabajadorEmail(auth.getName());
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(lista));
    }

    /**
     * Permite descargar una nómina en formato PDF usando su id.
     *
     * @param request objeto que contiene el id de la nómina
     * @return recurso PDF listo para descargar
     */
    @PermisoEmpresaTrabajador
    @Operation(
            summary = "Descargar una nómina",
            description = "Permite descargar el PDF de una nómina dada su id"
    )
    @PostMapping("/descargar")
    public ResponseEntity<ByteArrayResource> descargarNomina(@RequestBody IdRequestDto request) {
        NominaDownloadDto dto = nominaService.descargarNomina(request.getId());

        ByteArrayResource resource = new ByteArrayResource(dto.getArchivoPdf());
        MediaType mediaType = MediaType.parseMediaType(dto.getContentType());

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition
                                .attachment()
                                .filename(dto.getNombreArchivo())
                                .build()
                                .toString())
                .body(resource);
    }
}
