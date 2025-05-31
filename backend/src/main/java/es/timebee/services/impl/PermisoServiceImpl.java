package es.timebee.services.impl;

import es.timebee.domain.dto.*;
import es.timebee.domain.entity.Empresa;
import es.timebee.domain.entity.Permiso;
import es.timebee.domain.entity.Trabajador;
import es.timebee.domain.enumeration.EstadoPermiso;
import es.timebee.domain.enumeration.TipoPermiso;
import es.timebee.domain.mapper.PermisosMapper;
import es.timebee.domain.repository.EmpresaRepository;
import es.timebee.domain.repository.PermisoRepository;
import es.timebee.domain.repository.TrabajadorRepository;
import es.timebee.exception.ProcesoException;
import es.timebee.services.PermisoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio encargado de manejar los permisos laborales.
 * <p>
 * Este servicio no solo guarda datos: garantiza que solo quienes tienen derecho
 * puedan solicitar, editar, validar o eliminar permisos.
 *
 * Aquí, las empresas y los trabajadores interactúan bajo reglas claras.
 * Y cada método… cuida que esas reglas se cumplan.
 */
@Service
public class PermisoServiceImpl implements PermisoService {
    private final PermisoRepository permisoRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final PermisosMapper permisosMapper;
    private final EmpresaRepository empresaRepository;

    /**
     * Constructor que inyecta los repositorios y el mapper.
     * Los ingredientes clave para que este servicio funcione.
     *
     * @param permisoRepository repositorio de permisos
     * @param trabajadorRepository repositorio de trabajadores
     * @param permisosMapper mapper para transformar entidades a DTOs
     * @param empresaRepository repositorio de empresas
     */
    public PermisoServiceImpl(PermisoRepository permisoRepository, TrabajadorRepository trabajadorRepository, PermisosMapper permisosMapper,
                              EmpresaRepository empresaRepository) {
        this.permisoRepository = permisoRepository;
        this.trabajadorRepository = trabajadorRepository;
        this.permisosMapper = permisosMapper;
        this.empresaRepository = empresaRepository;
    }

    /**
     * Lista todos los tipos de permiso posibles.
     * <p>
     * Enumera los tipos disponibles, los envuelve en DTOs y los prepara
     * para que puedan mostrarse (y usarse) en el frontend.
     *
     * @return lista de tipos de permiso
     */
    @Override
    public List<TipoPermisoDto> listarTiposPermiso() {
        return Arrays.stream(TipoPermiso.values())
                .map(tp -> new TipoPermisoDto(
                        tp.getId(),
                        tp.getNombre(),
                        tp.name()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Permite a un trabajador solicitar un permiso.
     * <p>
     * Busca al trabajador, crea el permiso y lo guarda.
     * Si algo sale mal, lanza una excepción clara.
     *
     * @param dto datos del permiso a solicitar
     * @param emailTrabajador correo del trabajador que solicita
     * @return true si la solicitud fue exitosa
     */
    @Override
    public Boolean solicitarPermiso(PermisoSolicitarDto dto, String emailTrabajador) {
        Trabajador t = trabajadorRepository.findByEmailIgnoreCase(emailTrabajador)
                .orElseThrow(() -> new ProcesoException("Trabajador no encontrado: " + emailTrabajador));

        Permiso p = new Permiso();
        p.setTrabajador(t);
        p.setPermiso(dto.getPermiso());
        p.setFecha(dto.getFecha());
        p.setHora(dto.getHora());
        try {
            permisoRepository.save(p);
            return true;
        } catch (Exception e) {
            throw new ProcesoException("Error al solicitar un permiso",e);
        }
    }

    /**
     * Permite a un trabajador editar un permiso que aún no ha sido validado.
     * <p>
     * Solo se pueden editar permisos en estado SOLICITADO.
     *
     * @param dto datos editados del permiso
     * @param emailTrabajador correo del trabajador que edita
     * @return true si la edición fue exitosa
     */
    @Override
    public Boolean editarPermiso(PermisoEditarDto dto, String emailTrabajador) {
        Permiso permiso = permisoRepository.findById(dto.getId())
                .orElseThrow(() -> new ProcesoException("Permiso no encontrado: " + dto.getId()));

        if (permiso.getEstado() != EstadoPermiso.SOLICITADO) {
            throw new ProcesoException("Sólo se puede editar un permiso en estado SOLICITADO");
        }

        Optional<Trabajador> optTrabajador = trabajadorRepository.findByEmailIgnoreCase(emailTrabajador);

        if (!optTrabajador.get().getId().equals(permiso.getTrabajador().getId())) {
            throw new ProcesoException("No tienes permiso para editar esta solicitud.");
        }

        permiso.setPermiso(dto.getPermiso());
        permiso.setFecha(dto.getFecha());
        permiso.setHora(dto.getHora());
        try {
            permisoRepository.save(permiso);
            return true;
        } catch (Exception e) {
            throw new ProcesoException("Error al editar un permiso",e);
        }
    }

    /**
     * Permite a la empresa validar (aprobar o rechazar) un permiso.
     * <p>
     * La empresa debe ser la misma a la que pertenece el trabajador.
     *
     * @param dto datos de validación
     * @param emailEmpresa correo de la empresa que valida
     * @return true si la validación fue exitosa
     */
    @Override
    public Boolean validarPermiso(PermisoValidarDto dto, String emailEmpresa) {
        Permiso p = permisoRepository.findById(dto.getId())
                .orElseThrow(() -> new ProcesoException("Permiso no encontrado: " + dto.getId()));

        if (dto.getEstado() == EstadoPermiso.SOLICITADO) {
            throw new ProcesoException("Debe elegir APROBADO o RECHAZADO");
        }
        Long idEmpresaAlaQuePerteneceElTrabajadorDelPermiso = p.getTrabajador().getEmpresa().getId();
        Optional<Empresa> empresa = empresaRepository.findByEmail(emailEmpresa);
        Long idEmpresaQueRealizaOperacion = empresa.get().getId();
        if (!idEmpresaAlaQuePerteneceElTrabajadorDelPermiso.equals(idEmpresaQueRealizaOperacion)) {
            throw new ProcesoException("No tienes permiso para validar este permiso.");
        }

        p.setEstado(dto.getEstado());
        try {
            permisoRepository.save(p);
            return true;
        } catch (Exception e) {
            throw new ProcesoException("Error al validar un permiso",e);
        }
    }

    /**
     * Permite a un trabajador eliminar un permiso, solo si aún no ha sido aprobado o rechazado.
     *
     * @param id id del permiso a eliminar
     * @param emailTrabajador correo del trabajador que elimina
     * @return true si la eliminación fue exitosa
     */
    @Override
    public Boolean eliminarPermiso(Long id,  String emailTrabajador) {
        Permiso p = permisoRepository.findById(id)
                .orElseThrow(() -> new ProcesoException("Permiso no encontrado: " + id));

        if (p.getEstado() != EstadoPermiso.SOLICITADO) {
            throw new ProcesoException("No se puede eliminar un permiso que ya está " + p.getEstado().name());
        }
        Optional<Trabajador> trabajador = trabajadorRepository.findByEmailIgnoreCase(emailTrabajador);
        Long idTrabajadorQueRealizaOperacion = trabajador.get().getId();
        Long idTrabajadorQuePertenecePermiso = p.getTrabajador().getId();
        if(!idTrabajadorQueRealizaOperacion.equals(idTrabajadorQuePertenecePermiso)){
            throw new ProcesoException("No tienes permiso para eliminar este permiso.");
        }

        try {
            permisoRepository.delete(p);
            return true;
        } catch (Exception e) {
            throw new ProcesoException("Error al eliminar el permiso", e);
        }
    }

    /**
     * Obtiene todos los permisos asociados a una empresa.
     *
     * @param emailEmpresa correo de la empresa
     * @return lista de permisos en formato DTO
     */
    @Override
    public List<PermisosDto> obtenerPermisosPorEmpresaId(String emailEmpresa) {

        Optional<Empresa> empresa = empresaRepository.findByEmail(emailEmpresa);
        Long empresaId = empresa.get().getId();
        return permisoRepository
                .findByTrabajador_Empresa_Id(empresaId)
                .stream()
                .map(permisosMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los permisos de un trabajador.
     * <p>
     * Verifica los permisos del usuario autenticado antes de devolver los datos.
     *
     * @param emailTrabajador correo del trabajador
     * @return lista de permisos en formato DTO
     */
    @Override
    public List<PermisosDto> obtenerPermisosPorTrabajadorId(String emailTrabajador) {
        Trabajador trabajador = trabajadorRepository.findByEmailIgnoreCase(emailTrabajador)
                .orElseThrow(() -> new ProcesoException("No se encontró el trabajador con email: " + emailTrabajador));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        boolean isEmpresa = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_EMPRESA"));
        boolean isTrabajador = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TRABAJADOR"));

        if (isEmpresa) {
            Empresa empresa = empresaRepository.findByEmail(authEmail)
                    .orElseThrow(() -> new ProcesoException("Empresa no encontrada: " + authEmail));
            if (!trabajador.getEmpresa().getId().equals(empresa.getId())) {
                throw new ProcesoException("No puedes ver permisos de trabajadores de otra empresa");
            }
        }
        else if (isTrabajador) {
            if (!trabajador.getEmail().equalsIgnoreCase(authEmail)) {
                throw new ProcesoException("Sólo puedes ver tus propios permisos");
            }
        }
        else {
            throw new ProcesoException("Acceso no autorizado");
        }

        return permisoRepository.findByTrabajador_Id(trabajador.getId())
                .stream()
                .map(permisosMapper::toDto)
                .collect(Collectors.toList());
    }
}
