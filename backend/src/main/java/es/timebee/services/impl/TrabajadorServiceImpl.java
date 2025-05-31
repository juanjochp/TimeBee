package es.timebee.services.impl;

import es.timebee.domain.dto.ActualizarTrabajadorDto;
import es.timebee.domain.dto.FichajesDto;
import es.timebee.domain.dto.TrabajadorDto;
import es.timebee.domain.entity.Trabajador;
import es.timebee.domain.mapper.FichajesMapper;
import es.timebee.domain.mapper.TrabajadorMapper;
import es.timebee.domain.repository.TrabajadorRepository;
import es.timebee.exception.ProcesoException;
import es.timebee.services.TrabajadorService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona la vida (digital) de los trabajadores.
 * <p>
 * Desde obtener sus datos y fichajes hasta actualizar sus perfiles,
 * este servicio cuida cada paso, asegurando coherencia, seguridad y,
 * sobre todo, respeto por la información sensible.
 */
@Service
@Transactional
public class TrabajadorServiceImpl implements TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final TrabajadorMapper trabajadorMapper;
    private final FichajesMapper fichajesMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor que inyecta los ingredientes necesarios.
     * Repositorios, mappers, codificadores: todo lo que hace falta
     * para que el servicio funcione sin sobresaltos.
     *
     * @param trabajadorRepository repositorio de trabajadores
     * @param trabajadorMapper mapper de trabajadores
     * @param fichajesMapper mapper de fichajes
     * @param passwordEncoder codificador de contraseñas
     */
    public TrabajadorServiceImpl(TrabajadorRepository trabajadorRepository, TrabajadorMapper trabajadorMapper, FichajesMapper fichajesMapper, PasswordEncoder passwordEncoder) {
        this.trabajadorRepository = trabajadorRepository;
        this.trabajadorMapper = trabajadorMapper;
        this.fichajesMapper = fichajesMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Obtiene la lista de fichajes asociados a un trabajador (usando su email).
     * <p>
     * Una función que permite al trabajador consultar, revisar y,
     * quizás, recordar sus jornadas.
     *
     * @param email correo del trabajador
     * @return lista de fichajes en formato DTO
     */
   @Override
    public List<FichajesDto> getFichajesPorEmailTrabajador(String email) {
        return trabajadorRepository.findByFichajes_Trabajador_EmailEqualsIgnoreCase(email)
                .stream()
                .map(fichajesMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene los datos básicos de un trabajador.
     * <p>
     * Una fotografía (digital) rápida del trabajador,
     * perfecta para mostrar en perfiles, dashboards o editores.
     *
     * @param email correo del trabajador
     * @return DTO con la información del trabajador
     */
    @Override
    public TrabajadorDto getTrabajador(String email) {
        return trabajadorRepository.findByEmailIgnoreCase(email).map(trabajadorMapper::toDto).get();
    }

    /**
     * Actualiza los datos personales de un trabajador.
     * <p>
     * Puede incluir o no el cambio de contraseña. Valida cuidadosamente
     * que todo esté en orden antes de actualizar.
     *
     * @param peticion objeto con los nuevos datos
     * @param emailTrabajador correo del trabajador que solicita la actualización
     * @return true si la actualización fue completa; false si solo se actualizó parcialmente
     */
    @Override
    public Boolean actualizarTrabajador(ActualizarTrabajadorDto peticion, String emailTrabajador) {
        Trabajador e = trabajadorRepository.findByEmailIgnoreCase(emailTrabajador)
                .orElseThrow(() -> new ProcesoException("Trabajador no encontrado"));

        if (peticion.getPasswordAntigua() == null && peticion.getPasswordNueva() == null) {
            trabajadorRepository.updateDatosPersonalesTrabajador(
                    peticion.getEmail(), peticion.getNombre(), peticion.getApellidos(),
                    peticion.getGenero(), peticion.getDni(), peticion.getIban(),
                    peticion.getFechaNacimiento(), String.valueOf(peticion.getTelefono()), emailTrabajador
            );

            if (!peticion.getEmail().equalsIgnoreCase(emailTrabajador)) {
                return false;
            }

        } else if (peticion.getPasswordAntigua() != null && peticion.getPasswordNueva() != null) {
            if (!passwordEncoder.matches(peticion.getPasswordAntigua(), e.getPassword())) {
                throw new ProcesoException("La contraseña actual no es correcta");
            }
            String newHashed = passwordEncoder.encode(peticion.getPasswordNueva());
            trabajadorRepository.updateDatosPersonalesPasswordTrabajador(
                    newHashed,peticion.getEmail(), peticion.getNombre(), peticion.getApellidos(),
                    peticion.getGenero(), peticion.getDni(), peticion.getIban(),
                    peticion.getFechaNacimiento(), String.valueOf(peticion.getTelefono()), emailTrabajador
            );
            return false;
        } else {
            throw new ProcesoException("Uno de los campos de contraseña está vacío");
        }
        return true;
    }
}
