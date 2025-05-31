package es.timebee.services;

import es.timebee.domain.dto.ActualizarTrabajadorDto;
import es.timebee.domain.dto.FichajesDto;
import es.timebee.domain.dto.TrabajadorDto;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Servicio encargado de gestionar la información del trabajador.
 * <p>
 * Aquí vive todo lo que un trabajador necesita:
 * consultar sus fichajes, acceder a su perfil
 * y actualizar su información personal (¡incluida la contraseña!).
 */
public interface TrabajadorService {

    /**
     * Obtiene todos los fichajes realizados por el trabajador.
     * <p>
     * Ideal para mostrar historiales, jornadas acumuladas
     * o detalles de asistencia.
     *
     * @param email correo del trabajador
     * @return lista de fichajes en formato DTO
     */
    List<FichajesDto> getFichajesPorEmailTrabajador(String email);

    /**
     * Obtiene los datos personales del trabajador.
     * <p>
     * Un acceso rápido al perfil,
     * perfecto para paneles, editores o revisores.
     *
     * @param email correo del trabajador
     * @return DTO con la información del trabajador
     */
    TrabajadorDto getTrabajador(String email);

    /**
     * Actualiza los datos personales del trabajador.
     * <p>
     * Permite actualizar desde nombre y correo hasta,
     * si es necesario, la contraseña (con validación previa).
     *
     * @param peticion DTO con los datos actualizados
     * @param emailTrabajador correo del trabajador que solicita el cambio
     * @return true si se actualiza correctamente
     */
    Boolean actualizarTrabajador(@Valid ActualizarTrabajadorDto peticion, String emailTrabajador);
}
