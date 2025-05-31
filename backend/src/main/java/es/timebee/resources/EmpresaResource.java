package es.timebee.resources;

import es.timebee.domain.dto.*;
import es.timebee.domain.request.EmailRequest;
import es.timebee.security.annotations.PermisoAdmin;
import es.timebee.security.annotations.PermisoEmpresa;
import es.timebee.security.annotations.PermisoEmpresaAdmin;
import es.timebee.services.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static es.timebee.utils.ResponseUtils.generarRespuesta;

/**
 * {@code EmpresaResource} es el controlador REST principal que gestiona todas las operaciones
 * relacionadas con las empresas dentro de TimeBee.
 * <p>
 * Aquí se concentran acciones como consultar la empresa actual, listar todas las empresas,
 * gestionar los trabajadores asociados y trabajar con fichajes.
 * <p>
 * Este controlador está protegido con anotaciones de permisos, asegurando que solo los
 * usuarios autorizados puedan acceder a cada funcionalidad.
 */
@PermisoEmpresa
@RestController
@RequestMapping("/empresa")
@Tag(name = "Empresas", description = "Operaciones relacionadas con el manejo de empresas")
public class EmpresaResource {

    private final EmpresaService empresaService;

    /**
     * Constructor que inyecta el servicio de empresa.
     *
     * @param empresaService servicio que contiene la lógica de negocio para empresas
     */
    public EmpresaResource(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    /**
     * Obtiene la empresa asociada al email del usuario autenticado.
     *
     * @param auth objeto de autenticación proporcionado por Spring Security
     * @return la empresa encontrada envuelta en una respuesta estándar
     */
    @PermisoEmpresa
    @Operation(summary = "Obtener una empresa por email", description = "Obtener una empresa por email")
    @PostMapping
    public ResponseEntity<ApiRespuesta<EmpresaDto>> empresaPorEmail (Authentication auth) {
        return  ResponseEntity.ok().body(generarRespuesta(this.empresaService.getEmpresaPorEmail(auth.getName())));
    }

    /**
     * Actualiza los datos de una empresa.
     *
     * @param peticion datos de actualización
     * @param auth     autenticación del usuario
     * @return resultado de la operación (éxito o fallo)
     */
    @PermisoEmpresaAdmin
    @Operation(summary = "actualizar datos de una empresa", description = "actualizar datos de una empresa")
    @PostMapping("/actualizarempresa")
    @Transactional
    public ResponseEntity<ApiRespuesta<Boolean>> actualizarEmpresa (@Valid @RequestBody ActualizarEmpresaDto peticion, Authentication auth) {
        return  ResponseEntity.ok().body(generarRespuesta(this.empresaService.actualizarEmpresa(peticion, auth.getName())));
    }

    /**
     * Obtiene un listado completo de todas las empresas.
     *
     * @return lista de empresas
     */
    @PermisoAdmin
    @Operation(summary = "Obtener listado de empresas", description = "Obtener listado de empresas")
    @GetMapping("/listado")
    public ResponseEntity<ApiRespuesta<List<EmpresaDto>>> listadoEmpresas () {
        return  ResponseEntity.ok().body(generarRespuesta(this.empresaService.getAllEmpresas()));
    }

    /**
     * Obtiene los trabajadores asociados a la empresa autenticada.
     *
     * @param auth autenticación del usuario
     * @return lista de trabajadores de la empresa
     */
    @PermisoEmpresaAdmin
    @Operation(summary = "Obtener los trabajadores de una empresa por email", description = "Obtener los trabajadores de una empresa por email")
    @PostMapping("/trabajadores")
    public ResponseEntity<ApiRespuesta<List<TrabajadorDto>>> trabajadoresPorEmpresa (Authentication auth) {
        return  ResponseEntity.ok().body(generarRespuesta(this.empresaService.getTrabajadoresEmpresaPorEmail(auth.getName())));
    }

    /**
     * Obtiene los fichajes realizados en la empresa autenticada.
     *
     * @param auth autenticación del usuario
     * @return lista de fichajes
     */
    @PermisoEmpresaAdmin
    @Operation(summary = "Obtener los fichajes de una empresa por email", description = "Obtener los fichajes de una empresa por email")
    @PostMapping("/fichajes")
    public ResponseEntity<ApiRespuesta<List<FichajesDto>>> fichajesPorEmpresa (Authentication auth) {
        return  ResponseEntity.ok().body(generarRespuesta(this.empresaService.getFichajesEmpresaPorEmail(auth.getName())));
    }

    /**
     * Da de alta (registra) a un nuevo trabajador en la empresa.
     *
     * @param peticion datos del trabajador
     * @param auth     autenticación del usuario
     * @return resultado de la operación
     */
    @PermisoEmpresaAdmin
    @Operation(summary = "Dar de alta a un trabajador", description = "Dar de alta a un trabajador")
    @PostMapping("/altatrabajador")
    @Transactional
    public ResponseEntity<ApiRespuesta<Boolean>> altaTrabajador (@Valid @RequestBody TrabajadorDto peticion, Authentication auth) {
        return  ResponseEntity.ok().body(generarRespuesta(this.empresaService.altaTrabajador(peticion, auth.getName())));
    }

    /**
     * Da de baja (elimina/desactiva) a un trabajador por su email.
     *
     * @param emailTrabajador objeto que contiene el email del trabajador
     * @param auth            autenticación del usuario
     * @return resultado de la operación
     */
    @PermisoEmpresaAdmin
    @Operation(summary = "Dar de baja a un trabajador", description = "Dar de baja a un trabajador")
    @PostMapping("/bajatrabajador")
    @Transactional
    public ResponseEntity<ApiRespuesta<Boolean>> bajaTrabajador (@Valid @RequestBody EmailRequest emailTrabajador, Authentication auth) {
        return  ResponseEntity.ok().body(generarRespuesta(this.empresaService.bajaTrabajador(emailTrabajador.getEmail(), auth.getName())));
    }

    /**
     * Actualiza los datos de un trabajador de la empresa.
     *
     * @param peticion datos actualizados del trabajador
     * @param auth     autenticación del usuario
     * @return resultado de la operación
     */
    @PermisoEmpresaAdmin
    @Operation(summary = "actualizar datos de un trabajador", description = "actualizar datos de un trabajador")
    @PostMapping("/actualizartrabajador")
    @Transactional
    public ResponseEntity<ApiRespuesta<Boolean>> actualizarTrabajador (@Valid @RequestBody TrabajadorDto peticion, Authentication auth) {
        return  ResponseEntity.ok().body(generarRespuesta(this.empresaService.actualizarTrabajador(peticion, auth.getName())));
    }
}
