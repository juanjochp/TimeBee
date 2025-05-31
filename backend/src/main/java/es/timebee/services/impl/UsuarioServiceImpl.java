package es.timebee.services.impl;

import es.timebee.domain.dto.EmpresaDto;
import es.timebee.domain.entity.Empresa;
import es.timebee.domain.enumeration.Rol;
import es.timebee.domain.mapper.EmpresaMapper;
import es.timebee.domain.repository.EmpresaRepository;
import es.timebee.exception.ProcesoException;
import es.timebee.services.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Servicio que gestiona el alta de empresas.
 * <p>
 * Aquí nace cada empresa en el sistema: validando sus datos,
 * asegurando que no haya duplicados, asignándole su rol,
 * y dejando todo listo para que pueda empezar a trabajar.
 *
 * Un pequeño método, sí. Pero con mucho peso.
 */
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;
    //passwordEncoder es de spring framework
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor que inyecta los ingredientes clave:
     * el repositorio de empresas, el mapper para convertir DTOs,
     * y el codificador de contraseñas para mantener la seguridad.
     *
     * @param empresaRepository repositorio de empresas
     * @param empresaMapper mapper de empresas
     * @param passwordEncoder codificador de contraseñas
     */
    public UsuarioServiceImpl(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper, PasswordEncoder passwordEncoder) {
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Da de alta una nueva empresa en el sistema.
     * <p>
     * Primero verifica que no exista ya una empresa con el mismo email o CIF.
     * Si todo está libre, guarda la nueva empresa, asignándole el rol correspondiente
     * y almacenando su contraseña de forma segura.
     *
     * @param peticion DTO con los datos de la empresa a dar de alta
     * @return true si se guarda correctamente; lanza excepción si algo falla
     */
    @Override
    public Boolean altaEmpresa(EmpresaDto peticion) {
        // Verifica si ya existe una empresa con el mismo email
        if (empresaRepository.existsByEmailIgnoreCase(peticion.getEmail())) {
            throw new ProcesoException("Ya existe una empresa con el correo: " + peticion.getEmail());
        }
        // Verifica si ya existe una empresa con el mismo cif
        if (empresaRepository.existsByCifIgnoreCase(peticion.getCif())) {
            throw new ProcesoException("Ya existe una empresa con el mismo CIF");
        }

        try {
            Empresa empresa = empresaMapper.toEntity(peticion);
            empresa.setRol(Rol.EMPRESA);
            empresa.setEmail(peticion.getEmail());
            empresa.setActivo(true);

            empresa.setPassword(passwordEncoder.encode(peticion.getPassword()));
            empresaRepository.save(empresa);
            return true;
        } catch (Exception e) {
            throw new ProcesoException("Hubo un problema al dar de alta a la empresa", e);
        }
    }
}
