package es.timebee.resources;

import es.timebee.domain.dto.*;
import es.timebee.security.annotations.PermisoEmpresa;
import es.timebee.security.annotations.PermisoTrabajador;
import es.timebee.services.FichajeService;
import es.timebee.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * {@code FichajeResource} es el controlador REST encargado de manejar todas las operaciones
 * relacionadas con los fichajes de los trabajadores en TimeBee.
 * <p>
 * Aquí, tanto las empresas como los propios trabajadores pueden iniciar, editar o cerrar
 * fichajes según sus permisos. Es el corazón del control horario, asegurando que todas las
 * entradas y salidas queden registradas correctamente.
 * <p>
 * Este recurso combina seguridad (a través de roles), validación y respuestas estandarizadas
 * para que tanto el backend como el frontend puedan trabajar de la mano sin problemas.
 */
@RestController
@RequestMapping("/fichajes")
@Tag(name = "Fichajes", description = "Operaciones relacionadas con los fichajes de trabajadores")
public class FichajeResource {

    private final FichajeService fichajeService;

    /**
     * Constructor que inyecta el servicio de fichajes.
     *
     * @param fichajeService servicio que maneja la lógica de negocio de los fichajes
     */
    public FichajeResource(FichajeService fichajeService) {
        this.fichajeService = fichajeService;
    }

    /**
     * Permite a la empresa crear un nuevo fichaje para un trabajador.
     *
     * @param fichajeRequestDto datos del fichaje a crear
     * @param auth              autenticación del usuario
     * @return resultado de la operación (éxito o fallo)
     */
    @PermisoEmpresa
    @Operation(summary = "Crear un fichaje", description = "Permite a la empresa crear un nuevo fichaje para un trabajador")
    @PostMapping("/crear")
    public ResponseEntity<ApiRespuesta<Boolean>> crearFichaje(@Valid @RequestBody FichajeRequestDto fichajeRequestDto, Authentication auth) {
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(fichajeService.crearFichaje(fichajeRequestDto, auth.getName())));
    }

    /**
     * Permite a la empresa editar un fichaje existente de un trabajador.
     *
     * @param fichajeEditRequestDto datos actualizados del fichaje
     * @param auth                  autenticación del usuario
     * @return resultado de la operación
     */
    @PermisoEmpresa
    @Operation(summary = "Editar un fichaje", description = "Permite a la empresa editar un fichaje existente de un trabajador")
    @PostMapping("/editar")
    public ResponseEntity<ApiRespuesta<Boolean>> editarFichaje(@Valid @RequestBody FichajeEditRequestDto fichajeEditRequestDto, Authentication auth) {
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(fichajeService.editarFichaje(fichajeEditRequestDto, auth.getName())));
    }

    /**
     * Permite a la empresa eliminar un fichaje de un trabajador mediante su id.
     *
     * @param request objeto que contiene el id del fichaje a eliminar
     * @param auth    autenticación del usuario
     * @return resultado de la operación
     */
    @PermisoEmpresa
    @Operation(summary = "Eliminar un fichaje", description = "Permite a la empresa eliminar un fichaje de un trabajador mediante su id")
    @PostMapping("/eliminar")
    public ResponseEntity<ApiRespuesta<Boolean>> eliminarFichaje(@RequestBody IdRequestDto request, Authentication auth) {
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(fichajeService.eliminarFichaje(request.getId(), auth.getName())));
    }

    /**
     * Permite al trabajador iniciar su fichaje, es decir, marcar el momento de entrada.
     *
     * @param auth autenticación del usuario
     * @return resultado de la operación
     */
    @PermisoTrabajador
    @Operation(summary = "Iniciar fichaje", description = "El trabajador inicia su fichaje (solo fechaInicio) mediante su id")
    @PostMapping("/iniciar")
    public ResponseEntity<ApiRespuesta<Boolean>> iniciarFichaje(
            Authentication auth) {
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(fichajeService.iniciarFichaje(auth.getName())));
    }

    /**
     * Permite al trabajador finalizar su último fichaje abierto, es decir, marcar el momento de salida.
     *
     * @param auth autenticación del usuario
     * @return resultado de la operación
     */
    @PermisoTrabajador
    @Operation(summary = "Finalizar fichaje", description = "El trabajador cierra su último fichaje abierto mediante su id")
    @PostMapping("/finalizar")
    public ResponseEntity<ApiRespuesta<Boolean>> finalizarFichaje(
            Authentication auth) {
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(fichajeService.finalizarFichaje(auth.getName())));
    }
}
