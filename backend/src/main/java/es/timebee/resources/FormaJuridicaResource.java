package es.timebee.resources;

import es.timebee.domain.dto.ApiRespuesta;
import es.timebee.domain.dto.FormaJuridicaDto;
import es.timebee.services.FormaJuridicaService;
import es.timebee.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@code FormaJuridicaResource} es el controlador REST que se encarga de manejar
 * todas las peticiones relacionadas con las formas jurídicas disponibles en TimeBee.
 * <p>
 * Las formas jurídicas son los tipos legales que puede tener una empresa, como
 * Sociedad Limitada, Autónomo, Cooperativa, etc. Este recurso permite a los clientes
 * obtener la lista completa para mostrarla, por ejemplo, en formularios de registro.
 * <p>
 * Es simple pero esencial: facilita que los usuarios escojan correctamente el tipo
 * de empresa al darse de alta.
 */
@RestController
@RequestMapping("/formasjuridicas")
@Tag(name = "Formas Jurídicas", description = "Listado de formas jurídicas disponibles para Empresas")
public class FormaJuridicaResource {

    private final FormaJuridicaService formaJuridicaService;

    /**
     * Constructor que inyecta el servicio responsable de manejar las formas jurídicas.
     *
     * @param formaJuridicaService servicio de lógica de negocio para formas jurídicas
     */
    public FormaJuridicaResource(FormaJuridicaService formaJuridicaService) {
        this.formaJuridicaService = formaJuridicaService;
    }

    /**
     * Devuelve la lista de todas las formas jurídicas disponibles.
     * <p>
     * Esto incluye identificador, nombre y código interno para cada tipo.
     * Se usa principalmente en formularios de frontend para que los usuarios
     * puedan seleccionar el tipo correcto.
     *
     * @return respuesta con la lista de formas jurídicas disponibles
     */
    @Operation(
            summary = "Listar formas jurídicas",
            description = "Devuelve todas las formas jurídicas disponibles (id, nombre y código)"
    )
    @GetMapping
    public ResponseEntity<ApiRespuesta<List<FormaJuridicaDto>>> listarFormasJuridicas() {
        List<FormaJuridicaDto> lista = formaJuridicaService.listarFormasJuridicas();
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(lista));
    }
}
