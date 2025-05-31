package es.timebee.resources;

import es.timebee.domain.dto.*;
import es.timebee.security.annotations.PermisoEmpresaTrabajadorAdmin;
import es.timebee.security.annotations.PermisoTrabajador;
import es.timebee.services.TrabajadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static es.timebee.utils.ResponseUtils.generarRespuesta;

/**
 * {@code TrabajadorResource} es el controlador REST encargado de gestionar
 * todas las operaciones relacionadas con los trabajadores en TimeBee.
 * <p>
 * Aquí se manejan consultas de datos personales, fichajes y la actualización
 * de la información del trabajador,  de manera segura y organizada.
 */
@RestController
@RequestMapping("/trabajador")
@Tag(name = "Trabajadores", description = "Operaciones relacionadas con el manejo de trabajadores")
public class TrabajadorResource {

    private final TrabajadorService trabajadorService;

    /**
     * Constructor que inyecta el servicio de trabajador.
     *
     * @param trabajadorService servicio que maneja la lógica del trabajador
     */
    public TrabajadorResource(TrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }

    /**
     * Obtiene todos los fichajes asociados al trabajador autenticado, usando su email.
     *
     * @param auth objeto de autenticación del usuario
     * @return respuesta con la lista de fichajes del trabajador
     */
    @PermisoEmpresaTrabajadorAdmin
    @Operation(summary = "Obtener los fichajes de un trabajador por email", description = "Obtener los fichajes de un trabajador por email")
    @PostMapping("/fichajes")
    public ResponseEntity<ApiRespuesta<List<FichajesDto>>> fichajesPorEmailTrabajador (Authentication auth) {
        return  ResponseEntity.ok().body(generarRespuesta(this.trabajadorService.getFichajesPorEmailTrabajador(auth.getName())));
    }

    /**
     * Obtiene los datos personales del trabajador autenticado.
     *
     * @param auth objeto de autenticación del usuario
     * @return respuesta con los datos del trabajador
     */
    @PermisoEmpresaTrabajadorAdmin
    @Operation(summary = "Obtener los datos de un trabajador por email", description = "Obtener los datos de un trabajador por email")
    @PostMapping
    public ResponseEntity<ApiRespuesta<TrabajadorDto>> obtenerTrabajador (Authentication auth) {
        return  ResponseEntity.ok().body(generarRespuesta(this.trabajadorService.getTrabajador(auth.getName())));
    }

    /**
     * Permite al trabajador actualizar sus datos personales.
     *
     * @param peticion objeto con los datos a actualizar
     * @param auth     objeto de autenticación del usuario
     * @return respuesta indicando si la actualización fue exitosa
     */
    @PermisoTrabajador
    @Operation(summary = "actualizar datos de un trabajador", description = "actualizar datos de un trabajador")
    @PostMapping("/actualizartrabajador")
    @Transactional
    public ResponseEntity<ApiRespuesta<Boolean>> actualizarTrabajador (@Valid @RequestBody ActualizarTrabajadorDto peticion, Authentication auth) {
        return  ResponseEntity.ok().body(generarRespuesta(this.trabajadorService.actualizarTrabajador(peticion, auth.getName())));
    }
}
