package es.timebee.security;

import es.timebee.domain.entity.Empresa;
import es.timebee.domain.entity.Trabajador;
import es.timebee.domain.repository.EmpresaRepository;
import es.timebee.domain.repository.TrabajadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * {@code CustomUserDetailsService} es el corazón de la autenticación
 * en TimeBee.
 * <p>
 * Se encarga de cargar los detalles del usuario (ya sea una empresa
 * o un trabajador) desde la base de datos, basándose en su email.
 * Spring Security usa este servicio para autenticar usuarios cuando
 * intentan iniciar sesión.
 * <p>
 * 🚀 ¿Cómo funciona?
 * <ol>
 *     <li>Primero busca si el email pertenece a una {@code Empresa}.</li>
 *     <li>Si no lo encuentra, prueba en {@code Trabajador}.</li>
 *     <li>Si tampoco lo encuentra, lanza una {@code UsernameNotFoundException}.</li>
 * </ol>
 * <p>
 * Además, construye un objeto {@code UserDetails} que Spring Security
 * usa internamente para manejar las credenciales y los roles
 * (autorizaciones) del usuario.
 *
 * <p>Detalles clave:
 * <ul>
 *     <li>Usa el repositorio {@code EmpresaRepository} para consultar empresas.</li>
 *     <li>Usa el repositorio {@code TrabajadorRepository} para consultar trabajadores.</li>
 *     <li>El {@code UserDetails} devuelto incluye el email, la contraseña
 *     (ya codificada) y la lista de {@code GrantedAuthority} (roles).</li>
 * </ul>
 *
 * ¡Sin este servicio, no podríamos autenticar a nadie! Es uno de esos
 * componentes que trabajan silenciosamente en el fondo para que todo funcione.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmpresaRepository empresaRepository;
    private final TrabajadorRepository trabajadorRepository;

    @Autowired
    public CustomUserDetailsService(EmpresaRepository empresaRepository,
                                    TrabajadorRepository trabajadorRepository) {
        this.empresaRepository = empresaRepository;
        this.trabajadorRepository = trabajadorRepository;
    }

    /**
     * Busca al usuario por email en las entidades Empresa o Trabajador.
     *
     * @param email el email del usuario que intenta iniciar sesión.
     * @return un {@code UserDetails} con los datos de autenticación.
     * @throws UsernameNotFoundException si no se encuentra ningún usuario.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Empresa> optE = empresaRepository.findByEmail(email);
        if (optE.isPresent()) {
            Empresa e = optE.get();
            List<GrantedAuthority> auths = List.of(
                    new SimpleGrantedAuthority("ROLE_" + e.getRol().name())
            );
            return new User(e.getEmail(), e.getPassword(), auths);
        }

        Optional<Trabajador> optT = trabajadorRepository.findByEmailIgnoreCase(email);
        if (optT.isPresent()) {
            Trabajador t = optT.get();
            List<GrantedAuthority> auths = List.of(
                    new SimpleGrantedAuthority("ROLE_" + t.getRol().name())
            );
            return new User(t.getEmail(), t.getPassword(), auths);
        }

        throw new UsernameNotFoundException("No existe usuario con email: " + email);
    }

    /**
     * Construye el objeto {@code UserDetails} para una Empresa.
     *
     * @param empresa entidad Empresa.
     * @return el {@code UserDetails} listo para Spring Security.
     */
    private UserDetails buildUserDetails(Empresa empresa) {

        GrantedAuthority authority =
                new SimpleGrantedAuthority(empresa.getRol().name());
        return new org.springframework.security.core.userdetails.User(
                empresa.getEmail(),
                empresa.getPassword(),
                empresa.isActivo(),
                true,
                true,
                true,
                Collections.singletonList(authority)
        );
    }

    /**
     * Construye el objeto {@code UserDetails} para un Trabajador.
     *
     * @param trabajador entidad Trabajador.
     * @return el {@code UserDetails} listo para Spring Security.
     */
    private UserDetails buildUserDetails(Trabajador trabajador) {
        GrantedAuthority authority =
                new SimpleGrantedAuthority(trabajador.getRol().name());
        return new org.springframework.security.core.userdetails.User(
                trabajador.getEmail(),
                trabajador.getPassword(),
                trabajador.isActivo(),
                true,
                true,
                true,
                Collections.singletonList(authority)
        );
    }
}
