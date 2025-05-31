package es.timebee.resources;

import es.timebee.domain.dto.ApiRespuesta;
import es.timebee.domain.dto.GeneroDto;
import es.timebee.services.GeneroService;
import es.timebee.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@code GeneroResource} es el controlador REST que maneja las operaciones
 * relacionadas con los géneros disponibles en TimeBee.
 * <p>
 * Este recurso es sencillo pero muy importante, ya que permite mostrar en el frontend
 * la lista de géneros que los trabajadores pueden elegir al registrarse o editar su perfil.
 * Por ahora, maneja solo la lectura (GET), devolviendo todos los géneros posibles.
 */
@RestController
@RequestMapping("/generos")
@Tag(name = "Generos", description = "Listado de géneros disponibles para Trabajadores")
public class GeneroResource {

    private final GeneroService generoService;

    /**
     * Constructor que inyecta el servicio de géneros.
     *
     * @param generoService servicio responsable de la lógica relacionada con los géneros
     */
    public GeneroResource(GeneroService generoService) {
        this.generoService = generoService;
    }

    /**
     * Endpoint para obtener la lista completa de géneros disponibles.
     * <p>
     * Cada género incluye un id, un nombre legible y un código técnico (como FEMENINO, MASCULINO, etc.).
     * Esta información es especialmente útil para desplegar opciones en formularios, combos o menús.
     *
     * @return una respuesta estándar con la lista de géneros disponibles
     */
    @Operation(
            summary = "Listar géneros",
            description = "Devuelve todos los géneros disponibles (id, nombre y código)"
    )
    @GetMapping
    public ResponseEntity<ApiRespuesta<List<GeneroDto>>> listarGeneros() {
        List<GeneroDto> lista = generoService.listarGeneros();
        return ResponseEntity.ok(ResponseUtils.generarRespuesta(lista));
    }
}
