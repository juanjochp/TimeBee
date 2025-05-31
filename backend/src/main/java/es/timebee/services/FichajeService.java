package es.timebee.services;

import es.timebee.domain.dto.FichajeEditRequestDto;
import es.timebee.domain.dto.FichajeRequestDto;

/**
 * Servicio que define las operaciones clave para gestionar fichajes.
 * <p>
 * Aquí vive el núcleo del control horario:
 * crear, editar, eliminar, iniciar y finalizar fichajes.
 * En otras palabras: controlar cuándo empieza y termina cada jornada.
 */
public interface FichajeService {

    /**
     * Crea un nuevo fichaje para un trabajador.
     * <p>
     * Registra una entrada (o una salida planificada) en el sistema,
     * asegurando que los datos estén completos y validados.
     *
     * @param fichajeRequestDto datos del fichaje a crear
     * @param emailEmpresa correo de la empresa responsable
     * @return true si se crea correctamente
     */
    Boolean crearFichaje(FichajeRequestDto fichajeRequestDto, String emailEmpresa);

    /**
     * Edita un fichaje existente.
     * <p>
     * Permite corregir errores o ajustar datos en fichajes ya registrados.
     *
     * @param fichajeEditRequestDto datos editados del fichaje
     * @param emailEmpresa correo de la empresa responsable
     * @return true si se edita correctamente
     */
    Boolean editarFichaje(FichajeEditRequestDto fichajeEditRequestDto, String emailEmpresa);

    /**
     * Elimina un fichaje del sistema.
     * <p>
     * Siempre bajo permisos controlados, asegura que solo se borren
     * los fichajes autorizados.
     *
     * @param id identificador del fichaje
     * @param emailEmpresa correo de la empresa responsable
     * @return true si se elimina correctamente
     */
    Boolean eliminarFichaje(Long id, String emailEmpresa);

    /**
     * Inicia un nuevo fichaje (marca de entrada) para un trabajador.
     * <p>
     * Registra el momento en que la jornada laboral comienza.
     *
     * @param trabajadorEmail correo del trabajador
     * @return true si se inicia correctamente
     */
    Boolean iniciarFichaje(String trabajadorEmail);

    /**
     * Finaliza el fichaje abierto (marca de salida) de un trabajador.
     * <p>
     * Cierra oficialmente la jornada laboral.
     *
     * @param trabajadorEmail correo del trabajador
     * @return true si se finaliza correctamente
     */
    Boolean finalizarFichaje(String trabajadorEmail);
}
