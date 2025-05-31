package es.timebee.resources;

import es.timebee.domain.dto.ApiRespuesta;
import es.timebee.exception.ProcesoException;
import es.timebee.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code CustomControllerAdvice} es la clase encargada de manejar las excepciones globales
 * que ocurren durante la ejecución de la aplicación TimeBee.
 * <p>
 * Gracias a esta clase, cualquier error inesperado, de validación o de autorización
 * es capturado y devuelto al usuario de manera clara y estructurada, evitando que
 * la aplicación se rompa o muestre mensajes confusos.
 * <p>
 * Además, utiliza logs para registrar cada error, lo que facilita mucho
 * el trabajo de mantenimiento y depuración.
 */
@RestControllerAdvice
public class CustomControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(CustomControllerAdvice.class);

    /**
     * Maneja las excepciones personalizadas {@link ProcesoException}.
     *
     * @param ex      la excepción lanzada
     * @param request el contexto web donde ocurrió el error
     * @return una respuesta con estado {@code 400 Bad Request} y un mensaje de error detallado
     */
    @ExceptionHandler(ProcesoException.class)
    public ResponseEntity<ApiRespuesta<String>> handleProcesoException(ProcesoException ex, WebRequest request) {
        // Loguea la excepción completa
        logger.error("ProcesoException capturada: ", ex);

        ApiRespuesta<String> response = ResponseUtils.generarRespuestaError(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones de autorización {@link AuthorizationDeniedException}.
     *
     * @param ex      la excepción lanzada
     * @param request el contexto web donde ocurrió el error
     * @return una respuesta con estado {@code 403 Forbidden} y un mensaje de acceso denegado
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiRespuesta<String>> handleProcesoException(AuthorizationDeniedException ex, WebRequest request) {
        // Loguea la excepción completa
        logger.error("AuthorizationDeniedException capturada: ", ex);

        ApiRespuesta<String> response = ResponseUtils.generarRespuestaError(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /**
     * Maneja cualquier excepción general no contemplada específicamente.
     *
     * @param ex      la excepción lanzada
     * @param request el contexto web donde ocurrió el error
     * @return una respuesta con estado {@code 500 Internal Server Error} y un mensaje genérico
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiRespuesta<String>> handleGlobalException(Exception ex, WebRequest request) {
        // Loguea la excepción completa
        logger.error("Excepción global capturada: ", ex);

        ApiRespuesta<String> response = ResponseUtils.generarRespuestaError(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja los errores de validación cuando fallan las anotaciones {@code @Valid}.
     *
     * @param ex      la excepción de validación
     * @param request el contexto web donde ocurrió el error
     * @return una respuesta con estado {@code 400 Bad Request} y detalles de los campos con errores
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiRespuesta<String>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {

        logger.error("Errores de validación capturados: ", ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        String errorMsg = "Errores de validación: " + errors.toString();
        ApiRespuesta<String> response = ResponseUtils.generarRespuestaError(errorMsg);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}