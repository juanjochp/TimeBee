package es.timebee.resources;

import es.timebee.security.JwtUtil;
import es.timebee.security.dto.LoginRequest;
import es.timebee.security.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * {@code AuthController} gestiona las operaciones relacionadas con la autenticación
 * dentro de la aplicación TimeBee.
 * <p>
 * Expone un endpoint REST que permite a los usuarios iniciar sesión usando su email
 * y contraseña, generando un token JWT que será usado para las siguientes peticiones.
 * <p>
 * Este controlador es clave para proteger el acceso a los recursos y garantizar
 * que solo los usuarios autenticados puedan interactuar con las funcionalidades
 * protegidas de la aplicación.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    /**
     * Constructor que inyecta las dependencias necesarias para la autenticación.
     *
     * @param authManager el {@link AuthenticationManager} usado para verificar las credenciales
     * @param jwtUtil     la utilidad para generar y validar tokens JWT
     */
    @Autowired
    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Endpoint POST para iniciar sesión en TimeBee.
     * <p>
     * Recibe las credenciales del usuario, las autentica usando Spring Security
     * y, si son correctas, devuelve un token JWT para ser usado en futuras peticiones.
     *
     * @param req el objeto {@link LoginRequest} con el email y la contraseña del usuario
     * @return un {@link ResponseEntity} con el {@link LoginResponse} que incluye el token
     *         o un estado {@code 401 Unauthorized} si las credenciales son inválidas
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        try {
            // Intenta autenticar con Spring Security
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );
            // Si OK, generamos token
            String token = jwtUtil.generateToken((UserDetails) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (BadCredentialsException | UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
