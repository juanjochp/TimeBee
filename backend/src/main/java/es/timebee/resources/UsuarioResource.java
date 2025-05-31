package es.timebee.resources;

import es.timebee.domain.dto.ApiRespuesta;
import es.timebee.domain.dto.EmpresaDto;
import es.timebee.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static es.timebee.utils.ResponseUtils.generarRespuesta;

/**
 * {@code UsuarioResource} es el controlador REST responsable de manejar
 * las operaciones relacionadas con el registro de nuevas empresas en TimeBee.
 * <p>
 * Aquí se centraliza el alta (sign up) de empresas que desean usar la plataforma.
 */
@RestController
@RequestMapping("/registro")
public class UsuarioResource {

    private final UsuarioService usuarioService;

    /**
     * Constructor que inyecta el servicio de usuario.
     *
     * @param usuarioService servicio que maneja las operaciones de usuario
     */
    public UsuarioResource(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Registra una nueva empresa en el sistema.
     *
     * @param peticion objeto con los datos de la empresa a registrar
     * @return respuesta indicando si el alta fue exitosa
     */
    @Operation(summary = "Sing in de una empresa", description = "Sing in de una empresa")
    @PostMapping("/empresa")
    @Transactional
    public ResponseEntity<ApiRespuesta<Boolean>> altaEmpresa (@Valid @RequestBody EmpresaDto peticion) {
        return  ResponseEntity.ok().body(generarRespuesta(this.usuarioService.altaEmpresa(peticion)));
    }
}
