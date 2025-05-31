package es.timebee.resources;

import es.timebee.domain.dto.*;
import es.timebee.security.annotations.PermisoEmpresa;
import es.timebee.security.annotations.PermisoEmpresaTrabajador;
import es.timebee.security.annotations.PermisoTrabajador;
import es.timebee.services.PermisoService;
import es.timebee.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@code PermisoResource} es el controlador REST que gestiona
 * las operaciones relacionadas con los permisos de los trabajadores.
 * <p>
 * Aquí se encuentra todo lo necesario para solicitar, editar, eliminar y validar permisos,
 * tanto desde el lado del trabajador como desde el de la empresa.
 * <p>
 * La clase ofrece endpoints que permiten a los usuarios interactuar
 * de manera sencilla y segura con los registros de permisos,
 * asegurando que todo el proceso esté bien gestionado y bajo control.
 */
@RestController
@RequestMapping("/permisos")
@Tag(name = "Permisos", description = "Operaciones relacionadas con los permisos de trabajadores")
public class PermisoResource {

    private final PermisoService permisoService;

    /**
     * Constructor que inyecta el servicio de permisos.
     *
     * @param permisoService servicio que maneja la lógica de permisos
     */
    public PermisoResource(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

    /**
     * Lista todos los tipos de permiso disponibles en el sistema.
     *
     * @return lista de tipos de permiso (id, nombre, código)
     */
    @PermisoEmpresaTrabajador
    @Operation(
            summary = "Listar tipos de permiso",
            description = "Devuelve todos los tipos de permiso disponibles (id, nombre y código)"
    )
    @GetMapping
    public ResponseEntity<ApiRespuesta<List<TipoPermisoDto>>> listarTiposPermiso() {
        List<TipoPermisoDto> tipos = permisoService.listarTiposPermiso();
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(tipos));
    }

    /**
     * Permite al trabajador solicitar un nuevo permiso.
     *
     * @param dto  datos del permiso a solicitar
     * @param auth autenticación del usuario
     * @return respuesta indicando si la solicitud fue exitosa
     */
    @PermisoTrabajador
    @Operation(summary = "Solicitar permiso", description = "El trabajador solicita un permiso")
    @PostMapping("/solicitar")
    public ResponseEntity<ApiRespuesta<Boolean>> solicitar(
            @Valid @RequestBody PermisoSolicitarDto dto, Authentication auth) {
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(
                permisoService.solicitarPermiso(dto, auth.getName())
        ));
    }

    /**
     * Permite al trabajador editar un permiso (solo si sigue en estado SOLICITADO).
     *
     * @param dto  datos actualizados del permiso
     * @param auth autenticación del usuario
     * @return respuesta indicando si la edición fue exitosa
     */
    @PermisoTrabajador
    @Operation(summary = "Editar permiso", description = "El trabajador edita un permiso si sigue en estado SOLICITADO")
    @PostMapping("/editar")
    public ResponseEntity<ApiRespuesta<Boolean>> editar(
            @Valid @RequestBody PermisoEditarDto dto, Authentication auth) {
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(
                permisoService.editarPermiso(dto, auth.getName())
        ));
    }

    /**
     * Permite a la empresa aprobar o rechazar un permiso.
     *
     * @param dto  datos de validación (aprobado/rechazado)
     * @param auth autenticación del usuario
     * @return respuesta indicando si la validación fue exitosa
     */
    @PermisoEmpresa
    @Operation(summary = "Validar permiso", description = "La empresa aprueba o rechaza un permiso")
    @PostMapping("/validar")
    public ResponseEntity<ApiRespuesta<Boolean>> validar(@Valid @RequestBody PermisoValidarDto dto, Authentication auth) {
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(permisoService.validarPermiso(dto, auth.getName())));
    }

    /**
     * Permite al trabajador eliminar un permiso si aún está en estado SOLICITADO.
     *
     * @param request objeto con el id del permiso a eliminar
     * @param auth    autenticación del usuario
     * @return respuesta indicando si la eliminación fue exitosa
     */
    @PermisoTrabajador
    @Operation(summary = "Eliminar permiso",description = "Permite al trabajador eliminar un permiso si aún está en estado SOLICITADO")
    @PostMapping("/eliminar")
    public ResponseEntity<ApiRespuesta<Boolean>> eliminarPermiso(
            @Valid @RequestBody IdRequestDto request, Authentication auth) {
        Boolean ok = permisoService.eliminarPermiso(request.getId(),auth.getName());
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(ok));
    }

    /**
     * Devuelve todos los permisos de los trabajadores de una empresa,
     * usando el id de la empresa.
     *
     * @param auth autenticación del usuario
     * @return lista de permisos
     */
    @PermisoEmpresa
    @Operation(summary = "Permisos de todos los trabajadores por el id de la empresa",description = "Devuelve todos los permisos de los trabajadores  por el id de la empresa")
    @PostMapping("/empresa/id")
    public ResponseEntity<ApiRespuesta<List<PermisosDto>>> permisosPorEmpresaId(
            Authentication auth) {
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(permisoService.obtenerPermisosPorEmpresaId(auth.getName())));
    }

    /**
     * Devuelve todos los permisos de un trabajador autenticado.
     *
     * @param auth autenticación del usuario
     * @return lista de permisos del trabajador
     */
    @PermisoEmpresaTrabajador
    @Operation(summary = "Permisos de un trabajador",description = "Devuelve todos los permisos de un trabajador por su id")
    @PostMapping("/trabajador/id")
    public ResponseEntity<ApiRespuesta<List<PermisosDto>>> permisosPorTrabajadorId(Authentication auth) {
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(permisoService.obtenerPermisosPorTrabajadorId(auth.getName())));
    }
}
