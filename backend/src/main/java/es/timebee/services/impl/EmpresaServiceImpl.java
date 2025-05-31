package es.timebee.services.impl;

import es.timebee.domain.dto.ActualizarEmpresaDto;
import es.timebee.domain.dto.EmpresaDto;
import es.timebee.domain.dto.FichajesDto;
import es.timebee.domain.dto.TrabajadorDto;
import es.timebee.domain.entity.Empresa;
import es.timebee.domain.entity.Trabajador;
import es.timebee.domain.enumeration.Rol;
import es.timebee.domain.mapper.EmpresaMapper;
import es.timebee.domain.mapper.FichajesMapper;
import es.timebee.domain.mapper.TrabajadorMapper;
import es.timebee.domain.repository.EmpresaRepository;
import es.timebee.domain.repository.TrabajadorRepository;
import es.timebee.exception.ProcesoException;
import es.timebee.services.EmpresaService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code EmpresaServiceImpl} es el corazón de la lógica de negocio relacionada con las empresas en TimeBee.
 * <p>
 * Esta clase implementa las operaciones definidas en {@link EmpresaService} y se encarga de:
 * <ul>
 *     <li>Gestionar los datos de las empresas.</li>
 *     <li>Administrar trabajadores asociados a una empresa (altas, bajas, actualizaciones).</li>
 *     <li>Obtener listas de fichajes y trabajadores.</li>
 * </ul>
 * <p>
 * Usa repositorios JPA y mappers para transformar entidades a DTOs, facilitando así la comunicación
 * entre la base de datos y las capas superiores (como los controladores REST).
 */
@Service
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;
    private final TrabajadorMapper trabajadorMapper;
    private final FichajesMapper fichajesMapper;
    private final TrabajadorRepository trabajadorRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor para inyectar todas las dependencias necesarias.
     *
     * @param empresaRepository        repositorio JPA de empresas.
     * @param empresaMapper            mapper de empresa.
     * @param trabajadorMapper         mapper de trabajador.
     * @param fichajesMapper           mapper de fichajes.
     * @param trabajadorRepository     repositorio JPA de trabajadores.
     * @param passwordEncoder          codificador de contraseñas.
     */
    public EmpresaServiceImpl(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper,
                              TrabajadorMapper trabajadorMapper,
                              FichajesMapper fichajesMapper, TrabajadorRepository trabajadorRepository, PasswordEncoder passwordEncoder) {
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
        this.trabajadorMapper = trabajadorMapper;
        this.fichajesMapper = fichajesMapper;
        this.trabajadorRepository = trabajadorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Obtiene una lista de todas las empresas registradas.
     *
     * @return lista de {@link EmpresaDto}.
     */
    @Override
    public List<EmpresaDto> getAllEmpresas() {
        return empresaRepository.findAll()
                .stream()
                .map(empresaMapper::toDto)
                .collect(Collectors.toList());

    }

    /**
     * Busca una empresa por su email.
     *
     * @param email el email de la empresa.
     * @return DTO de la empresa encontrada.
     * @throws ProcesoException si no se encuentra la empresa.
     */
    @Override
    public EmpresaDto getEmpresaPorEmail(String email) {
        return empresaRepository.findByEmail(email).map(empresaMapper::toDto)
                .orElseThrow(() ->new ProcesoException("No se ha encontrado la empresa por el email solicitado.")) ;
    }

    /**
     * Obtiene todos los trabajadores asociados a una empresa por su email.
     *
     * @param email el email de la empresa.
     * @return lista de trabajadores en formato DTO.
     */
    @Override
    public List<TrabajadorDto> getTrabajadoresEmpresaPorEmail(String email) {
        return  empresaRepository.findByTrabajadores_Empresa_EmailEqualsIgnoreCase(email)
                .stream()
                .map(trabajadorMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los fichajes asociados a una empresa por su email.
     *
     * @param email el email de la empresa.
     * @return lista de fichajes en formato DTO.
     */
    @Override
    public List<FichajesDto> getFichajesEmpresaPorEmail(String email) {
        return empresaRepository.findByFichajes_Empresa_EmailEqualsIgnoreCase(email)
                .stream()
                .map(fichajesMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Da de alta a un nuevo trabajador en la empresa.
     *
     * @param peticion     los datos del trabajador.
     * @param emailEmpresa el email de la empresa que lo registra.
     * @return {@code true} si se ha registrado correctamente.
     * @throws ProcesoException si hay datos duplicados o errores al guardar.
     */
    @Override
    public Boolean altaTrabajador(TrabajadorDto peticion, String emailEmpresa) {
        // Verifica si ya existe un trabajador con el mismo email
        if (trabajadorRepository.existsByEmailIgnoreCase(peticion.getEmail())) {
            throw new ProcesoException("El email " + peticion.getEmail() + " ya se encuentra en uso");
        }
        // Verifica si ya existe una empresa con el mismo cif
        if (trabajadorRepository.existsByDniIgnoreCase(peticion.getDni())) {
            throw new ProcesoException("Ya existe un trabajador con el mismo DNI");
        }

        Empresa empresa = empresaRepository.findByEmail(emailEmpresa)
                .orElseThrow(() -> new ProcesoException("Empresa no encontrada"));

        try {
            peticion.setRol(Rol.TRABAJADOR);
            Trabajador trabajador = trabajadorMapper.toEntity(peticion);
            trabajador.setPassword(passwordEncoder.encode(trabajador.getPassword()));
            trabajador.setEmpresa(empresa);
            trabajador.setActivo(true);
            trabajadorRepository.save(trabajador);
            return true;
        } catch (Exception e) {
            throw new ProcesoException("Hubo un problema al crear el trabajador", e);
        }
    }

    /**
     * Da de baja (inactiva) a un trabajador.
     *
     * @param trabajadorEmail el email del trabajador.
     * @param empresaEmail    el email de la empresa.
     * @return {@code true} si se ha dado de baja correctamente.
     * @throws ProcesoException        si no se encuentra el trabajador.
     * @throws AccessDeniedException   si la empresa no es la propietaria del trabajador.
     */
    @Override
    public Boolean bajaTrabajador(String trabajadorEmail, String empresaEmail) {
        Trabajador t = trabajadorRepository.findByEmailIgnoreCase(trabajadorEmail)
                .orElseThrow(() -> new ProcesoException("Trabajador no encontrado"));
        if (!t.getEmpresa().getEmail().equalsIgnoreCase(empresaEmail)) {
            throw new AccessDeniedException("No puedes dar de baja a un trabajador que no es tuyo");
        }
        trabajadorRepository.inactivarTrabajador(trabajadorEmail);
        return true;
    }

    /**
     * Actualiza los datos personales de un trabajador.
     *
     * @param peticion      los nuevos datos del trabajador.
     * @param emailEmpresa  el email de la empresa que lo solicita.
     * @return {@code true} si se ha actualizado correctamente.
     * @throws ProcesoException      si no se encuentra el trabajador.
     * @throws AccessDeniedException si la empresa no es la propietaria.
     */
    @Override
    public boolean actualizarTrabajador(TrabajadorDto peticion, String emailEmpresa) {
        Trabajador t = trabajadorRepository.findById(peticion.getId())
                .orElseThrow(() -> new ProcesoException("Trabajador no encontrado"));
        if (!t.getEmpresa().getEmail().equalsIgnoreCase(emailEmpresa)) {
            throw new AccessDeniedException("No puedes modificar un trabajador que no es tuyo");
        }
        trabajadorRepository.updateDatosPersonalesTrabajadorDesdeEmpresa(
            peticion.getEmail(),
            peticion.getPassword(),
            peticion.isActivo(),
            peticion.getNombre(),
            peticion.getApellidos(),
            peticion.getDni(),
            peticion.getNaf(),
            peticion.getGenero(),
            peticion.getFechaNacimiento(),
            peticion.getFechaAntiguedad(),
            peticion.getCategoria(),
            peticion.getIban(),
            peticion.getTelefono(),
            peticion.getId()
        );
        return true;
    }

    /**
     * Actualiza los datos de la empresa, incluyendo (opcionalmente) la contraseña.
     *
     * @param peticion      los nuevos datos.
     * @param emailEmpresa  el email de la empresa actual.
     * @return {@code true} si se ha actualizado correctamente.
     * @throws ProcesoException si las contraseñas no coinciden o hay campos incompletos.
     */
    @Override
    public Boolean actualizarEmpresa(ActualizarEmpresaDto peticion, String emailEmpresa) {
        Empresa e = empresaRepository.findByEmail(emailEmpresa)
                .orElseThrow(() -> new ProcesoException("Empresa no encontrada"));

       if (peticion.getPasswordAntigua() == null && peticion.getPasswordNueva() == null) {
           empresaRepository.updateDatosPersonalesEmpresa(
                   peticion.getEmail(), peticion.getNombre(), peticion.getFormaJuridica(),
                   peticion.getCif(), peticion.getDireccion(), peticion.getTelefono(), emailEmpresa
           );

           if (!peticion.getEmail().equalsIgnoreCase(emailEmpresa)) {
               return false;
           }

       } else if (peticion.getPasswordAntigua() != null && peticion.getPasswordNueva() != null) {
           if (!passwordEncoder.matches(peticion.getPasswordAntigua(), e.getPassword())) {
               throw new ProcesoException("La contraseña actual no es correcta");
           }
           String newHashed = passwordEncoder.encode(peticion.getPasswordNueva());
           empresaRepository.updateDatosPersonalesPasswordEmpresa(
                   newHashed,peticion.getEmail(), peticion.getNombre(), peticion.getFormaJuridica(),
                   peticion.getCif(), peticion.getDireccion(), peticion.getTelefono(), emailEmpresa
           );
           return false;
       } else {
           throw new ProcesoException("Uno de los campos de contraseña está vacío");
       }
       return true;
    }
}
