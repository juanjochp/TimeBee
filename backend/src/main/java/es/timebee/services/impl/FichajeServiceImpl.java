package es.timebee.services.impl;

import es.timebee.domain.dto.FichajeEditRequestDto;
import es.timebee.domain.dto.FichajeRequestDto;
import es.timebee.domain.entity.Empresa;
import es.timebee.domain.entity.Fichaje;
import es.timebee.domain.entity.Trabajador;
import es.timebee.exception.ProcesoException;
import es.timebee.domain.repository.EmpresaRepository;
import es.timebee.domain.repository.FichajeRepository;
import es.timebee.domain.repository.TrabajadorRepository;
import es.timebee.services.FichajeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de fichajes.
 * <p>
 * Aquí se orquesta la magia: crear, editar, eliminar, iniciar y finalizar fichajes.
 * Es el lugar donde las operaciones clave de control horario cobran vida.
 *
 * Este servicio no solo maneja datos: garantiza coherencia, verifica permisos,
 * y levanta la mano (¡lanzando excepciones!) cuando algo no cuadra.
 *
 * Cada método tiene un propósito.
 */
@Service
@Transactional
public class FichajeServiceImpl implements FichajeService {

    private final FichajeRepository fichajeRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final EmpresaRepository empresaRepository;

    /**
     * Constructor que inyecta las dependencias necesarias.
     * Sin estos repositorios, este servicio estaría… incompleto.
     *
     * @param fichajeRepository repositorio de fichajes
     * @param trabajadorRepository repositorio de trabajadores
     * @param empresaRepository repositorio de empresas
     */
    public FichajeServiceImpl(FichajeRepository fichajeRepository,
                              TrabajadorRepository trabajadorRepository,
                              EmpresaRepository empresaRepository) {
        this.fichajeRepository = fichajeRepository;
        this.trabajadorRepository = trabajadorRepository;
        this.empresaRepository = empresaRepository;
    }

    /**
     * Crea un nuevo fichaje.
     * <p>
     * Busca trabajador. Verifica empresa. Comprueba pertenencia.
     * Si todo está bien… ¡lo guarda!
     *
     * @param fichajeRequestDto datos del fichaje a crear
     * @param emailEmpresa correo de la empresa (usado para validación)
     * @return true si todo sale bien; lanza excepción si no
     */
    @Override
    public Boolean crearFichaje(FichajeRequestDto fichajeRequestDto, String emailEmpresa) {
        Optional<Trabajador> optTrabajador = trabajadorRepository.findById(fichajeRequestDto.getTrabajadorId());
        if(optTrabajador.isEmpty()){
            throw new ProcesoException("No se encontró el trabajador con id: " + fichajeRequestDto.getTrabajadorId());
        }
        Trabajador trabajador = optTrabajador.get();

        Optional<Empresa> optEmpresa = empresaRepository.findByEmail(emailEmpresa);
        if(optEmpresa.isEmpty()){
            throw new ProcesoException("No se encontró la empresa con id: " + fichajeRequestDto.getEmpresaId());
        }
        Empresa empresa = optEmpresa.get();

       if(!trabajador.getEmpresa().getId().equals(empresa.getId())){
            throw new ProcesoException("El trabajador no pertenece a la empresa especificada.");
        }

        Fichaje fichaje = new Fichaje();
        fichaje.setTrabajador(trabajador);
        fichaje.setEmpresa(empresa);
        fichaje.setFechaInicio(fichajeRequestDto.getFechaInicio());
        if (fichajeRequestDto.getFechaFin() != null) {
            fichaje.setFechaFin(fichajeRequestDto.getFechaFin());
        } else {
            try {
                fichajeRepository.guardarFichajeSinCerrar(trabajador.getId(), empresa.getId(), fichajeRequestDto.getFechaInicio());
                return true;
            } catch (Exception e) {
                throw new ProcesoException("Hubo un error al guardar un fichaje.", e);
            }
        }

        try {
            fichajeRepository.save(fichaje);
            return true;
        } catch (Exception e) {
            throw new ProcesoException("Hubo un error al guardar un fichaje.", e);
        }
    }

    /**
     * Edita un fichaje existente.
     * <p>
     * Busca. Comprueba. Actualiza. Guarda.
     * Cuando algo falla, lo dice bien claro.
     *
     * @param fichajeEditRequestDto datos a actualizar
     * @param emailEmpresa correo de la empresa (para validar permisos)
     * @return true si la edición fue exitosa; lanza excepción si algo falla
     */
    @Override
    public Boolean editarFichaje(FichajeEditRequestDto fichajeEditRequestDto, String emailEmpresa) {
        Optional<Fichaje> optFichaje = fichajeRepository.findById(fichajeEditRequestDto.getId());
        if (optFichaje.isEmpty()) {
            throw new ProcesoException("No se encontró el fichaje con id: " + fichajeEditRequestDto.getId());
        }
        Fichaje fichajeExistente = optFichaje.get();

        Optional<Trabajador> optTrabajador = trabajadorRepository.findById(fichajeEditRequestDto.getTrabajadorId());
        if (optTrabajador.isEmpty()) {
            throw new ProcesoException("No se encontró el trabajador con id: " + fichajeEditRequestDto.getTrabajadorId());
        }
        Trabajador trabajador = optTrabajador.get();

        Optional<Empresa> optEmpresa = empresaRepository.findByEmail(emailEmpresa);
        if (optEmpresa.isEmpty()) {
            throw new ProcesoException("No se encontró la empresa con id: " + fichajeEditRequestDto.getEmpresaId());
        }
        Empresa empresa = optEmpresa.get();

        if (!trabajador.getEmpresa().getId().equals(empresa.getId())) {
            throw new ProcesoException("El trabajador no pertenece a la empresa especificada.");
        }

        fichajeExistente.setTrabajador(trabajador);
        fichajeExistente.setEmpresa(empresa);
        fichajeExistente.setFechaInicio(fichajeEditRequestDto.getFechaInicio());
        fichajeExistente.setFechaFin(fichajeEditRequestDto.getFechaFin());

        try {
            fichajeRepository.save(fichajeExistente);
            return true;
        } catch (Exception e) {
            throw new ProcesoException("Hubo un error al editar el fichaje.");
        }
    }

    /**
     * Elimina un fichaje.
     * <p>
     * Este método no perdona errores: verifica que el fichaje exista y
     * que realmente pertenezca a la empresa que lo solicita.
     *
     * @param id identificador del fichaje
     * @param emailEmpresa correo de la empresa
     * @return true si el borrado fue exitoso; lanza excepción si no
     */
    @Override
    public Boolean eliminarFichaje(Long id, String emailEmpresa) {
        Optional<Fichaje> optFichaje = fichajeRepository.findById(id);
        if (optFichaje.isEmpty()) {
            throw new ProcesoException("No se encontró el fichaje con id: " + id);
        }
        if (!optFichaje.get().getEmpresa().getEmail().equalsIgnoreCase(emailEmpresa)) {
            throw new ProcesoException("No tienes permisos para eliminar este fichaje.");
        }
        try {
            fichajeRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ProcesoException("Hubo un error al eliminar el fichaje.", e);
        }
    }

    /**
     * Inicia un nuevo fichaje para un trabajador.
     * <p>
     * Busca al trabajador. Verifica que no haya otro fichaje abierto.
     * Si todo está listo, ¡arranca!
     *
     * @param trabajadorEmail correo del trabajador
     * @return true si el inicio fue exitoso; lanza excepción si no
     */
    @Override
    public Boolean iniciarFichaje(String trabajadorEmail) {
        Trabajador trab = trabajadorRepository.findByEmailIgnoreCase(trabajadorEmail)
                .orElseThrow(() -> new ProcesoException("Trabajador no encontrado"));

        boolean hayAbierto = !fichajeRepository
                .findByTrabajador_IdAndFechaFinIsNullOrderByFechaInicioAsc(trab.getId())
                .isEmpty();

        if (hayAbierto) {
            throw new ProcesoException("Ya existe un fichaje abierto para el trabajador");
        }

        Fichaje f = new Fichaje();
        f.setTrabajador(trab);
        f.setEmpresa(trab.getEmpresa());
        f.setFechaInicio(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        try {
            fichajeRepository.save(f);
            return true;
        } catch (Exception e) {
            throw new ProcesoException("No se pudo iniciar el fichaje",e);
        }
    }

    /**
     * Finaliza el fichaje abierto de un trabajador.
     * <p>
     * Busca. Encuentra. Cierra.
     * Y si no hay nada que cerrar… te lo dice sin rodeos.
     *
     * @param emailTrabajador correo del trabajador
     * @return true si se finaliza correctamente; lanza excepción si algo falla
     */
    @Override
    public Boolean finalizarFichaje(String emailTrabajador) {
        Optional<Trabajador> trabajador = trabajadorRepository.findByEmailIgnoreCase(emailTrabajador);

        if (trabajador.isEmpty()){
            throw new ProcesoException("No se ha encontrado ningún trabajador");
        }

        Long trabajadorId = trabajador.get().getId();

        List<Fichaje> abiertos = fichajeRepository
                .findByTrabajador_IdAndFechaFinIsNullOrderByFechaInicioAsc(trabajadorId);

        if (abiertos.isEmpty()) {
            throw new ProcesoException("No hay fichajes abiertos para el trabajador");
        }
        Fichaje f = abiertos.get(0);
        f.setFechaFin(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        try {
            fichajeRepository.save(f);
            return true;
        } catch (Exception e) {
            throw new ProcesoException("No se pudo finalizar el fichaje",e);
        }
    }

}
