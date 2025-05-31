package es.timebee.services.impl;

import es.timebee.domain.dto.NominaDownloadDto;
import es.timebee.domain.dto.NominaMetadataDto;
import es.timebee.domain.entity.Empresa;
import es.timebee.domain.entity.Nomina;
import es.timebee.domain.entity.Trabajador;
import es.timebee.exception.ProcesoException;
import es.timebee.domain.repository.EmpresaRepository;
import es.timebee.domain.repository.NominaRepository;
import es.timebee.domain.repository.TrabajadorRepository;
import es.timebee.services.NominaService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio encargado de gestionar las nóminas.
 * <p>
 * Aquí se maneja algo más que datos: se trata de confianza, de seguridad,
 * de garantizar que solo las personas autorizadas puedan subir, consultar,
 * eliminar o descargar nóminas.
 *
 * Cada método late con un objetivo: proteger y servir, asegurando
 * que la información fluya solo hacia quien debe.
 */
@Service
@Transactional
public class NominaServiceImpl implements NominaService {

    private final NominaRepository nominaRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final EmpresaRepository empresaRepository;

    /**
     * Constructor que inyecta las dependencias necesarias.
     * Tres pilares: nóminas, trabajadores y empresas.
     *
     * @param nominaRepository repositorio de nóminas
     * @param trabajadorRepository repositorio de trabajadores
     * @param empresaRepository repositorio de empresas
     */
    public NominaServiceImpl(NominaRepository nominaRepository,
                             TrabajadorRepository trabajadorRepository,
                             EmpresaRepository empresaRepository) {
        this.nominaRepository = nominaRepository;
        this.trabajadorRepository = trabajadorRepository;
        this.empresaRepository = empresaRepository;
    }

    /**
     * Sube una nómina al sistema.
     * <p>
     * Valida que el trabajador exista, que la empresa esté correcta,
     * que el archivo sea un PDF… y si todo encaja,la guarda.
     *
     * @param trabajadorId id del trabajador
     * @param periodo mes/año de la nómina
     * @param file archivo PDF a subir
     * @param emailEmpresa correo de la empresa que sube la nómina
     * @return true si se guarda correctamente; lanza excepción si algo falla
     */
    @Override
    public Boolean subirNomina(Long trabajadorId, LocalDate periodo, MultipartFile file, String emailEmpresa) {
        Optional<Trabajador> optTrabajador = trabajadorRepository.findById(trabajadorId);
        if(optTrabajador.isEmpty()){
            throw new ProcesoException("No se encontró el trabajador con id: " + trabajadorId);
        }
        Trabajador trabajador = optTrabajador.get();

        Optional<Empresa> optEmpresa = empresaRepository.findByEmail(emailEmpresa);
        if(optEmpresa.isEmpty()){
            throw new ProcesoException("No se encontró la empresa");
        }
        Empresa empresa = optEmpresa.get();

        if(!trabajador.getEmpresa().getId().equals(empresa.getId())){
            throw new ProcesoException("El trabajador no pertenece a la empresa especificada.");
        }

        Nomina nomina = new Nomina();
        try {
            if (!"application/pdf".equalsIgnoreCase(file.getContentType())) {
                throw new ProcesoException("El archivo no es PDF");
            }
            nomina.setNombreArchivo(file.getOriginalFilename());
            nomina.setContentType(file.getContentType());
            nomina.setTamano(file.getSize());
            nomina.setArchivoPdf(file.getBytes());
        } catch (IOException e) {
            throw new ProcesoException("Error al leer el archivo PDF.", e);
        }
        nomina.setPeriodo(periodo);
        nomina.setTrabajador(trabajador);
        nomina.setEmpresa(empresa);

        try {
            nominaRepository.save(nomina);
            return true;
        } catch (Exception e) {
            throw new ProcesoException("Error al guardar la nómina en la base de datos.", e);
        }
    }

    /**
     * Elimina una nómina del sistema.
     * <p>
     * Aquí no hay margen para errores: verifica que la empresa tenga permiso
     * antes de proceder al borrado.
     *
     * @param id id de la nómina
     * @param emailEmpresa correo de la empresa
     * @return true si se elimina correctamente; lanza excepción si no
     */
    @Override
    public Boolean eliminarNomina(Long id, String emailEmpresa) {
        Optional<Nomina> opt = nominaRepository.findById(id);
        if (opt.isEmpty()) {
            throw new ProcesoException("No se encontró la nómina con id: " + id);
        }

        if (!opt.get().getEmpresa().getEmail().equalsIgnoreCase(emailEmpresa)) {
            throw new ProcesoException("No tienes permisos para eliminar esta nómina");
        }

        try {
            nominaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ProcesoException("Hubo un error al eliminar la nómina.", e);
        }
    }

    /**
     * Obtiene la lista de nóminas asociadas a un trabajador específico.
     * <p>
     * Verifica los permisos según el rol autenticado. Una vez validado,
     * construye una lista con los metadatos de las nóminas.
     *
     * @param trabajadorId id del trabajador
     * @return lista de metadatos de nóminas
     */
    @Override
    public List<NominaMetadataDto> obtenerNominasPorTrabajador(Long trabajadorId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        boolean isEmpresa = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_EMPRESA"));
        boolean isTrabajador = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TRABAJADOR"));

        Trabajador trabajador = trabajadorRepository.findById(trabajadorId)
                .orElseThrow(() -> new ProcesoException("No se encontró el trabajador con id: " + trabajadorId));

        if (isEmpresa) {
            Empresa miEmpresa = empresaRepository.findByEmail(authEmail)
                    .orElseThrow(() -> new ProcesoException("Empresa no encontrada: " + authEmail));
            if (!trabajador.getEmpresa().getId().equals(miEmpresa.getId())) {
                throw new AccessDeniedException("No puedes ver nóminas de un trabajador de otra empresa");
            }
        }
        else if (isTrabajador) {
            if (!trabajador.getEmail().equalsIgnoreCase(authEmail)) {
                throw new AccessDeniedException("Sólo puedes ver tus propias nóminas");
            }
        }
        else {
            throw new AccessDeniedException("Acceso no autorizado");
        }

        return nominaRepository.findByTrabajador_Id(trabajadorId)
                .stream()
                .map(n -> NominaMetadataDto.builder()
                        .id(n.getId())
                        .periodo(n.getPeriodo())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Obtiene la lista de nóminas para un trabajador, usando su correo electrónico.
     * <p>
     * Más directo, pensado para usos internos.
     *
     * @param trabajadorEmail correo del trabajador
     * @return lista de metadatos de nóminas
     */
    @Override
    public List<NominaMetadataDto> obtenerNominasPorTrabajadorEmail(String trabajadorEmail) {
        return nominaRepository.findByTrabajador_Email(trabajadorEmail)
                .stream()
                .map(n -> NominaMetadataDto.builder()
                        .id(n.getId())
                        .periodo(n.getPeriodo())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Descarga una nómina específica.
     * <p>
     * Este método es cuidadoso: verifica permisos, roles y pertenencias
     * antes de entregar el preciado PDF.
     *
     * @param id id de la nómina
     * @return DTO con el archivo PDF y sus metadatos
     */
    @Override
    public NominaDownloadDto descargarNomina(Long id) {
        Nomina nomina = nominaRepository.findById(id)
                .orElseThrow(() -> new ProcesoException("No se encontró la nómina con id: " + id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        boolean isEmpresa = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_EMPRESA"));
        boolean isTrabajador = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TRABAJADOR"));

        Trabajador trabajador = nomina.getTrabajador();
        Empresa empresaPropietaria = trabajador.getEmpresa();

        if (isEmpresa) {
            Empresa miEmpresa = empresaRepository.findByEmail(authEmail)
                    .orElseThrow(() -> new ProcesoException("Empresa no encontrada: " + authEmail));
            if (!empresaPropietaria.getId().equals(miEmpresa.getId())) {
                throw new AccessDeniedException("No puedes descargar nóminas de trabajadores de otra empresa");
            }
        }
        else if (isTrabajador) {
            if (!trabajador.getEmail().equalsIgnoreCase(authEmail)) {
                throw new AccessDeniedException("Sólo puedes descargar tus propias nóminas");
            }
        }
        else {
            throw new AccessDeniedException("Acceso no autorizado");
        }

        return NominaDownloadDto.builder()
                .nombreArchivo(nomina.getNombreArchivo())
                .contentType(nomina.getContentType())
                .archivoPdf(nomina.getArchivoPdf())
                .build();
    }

}
