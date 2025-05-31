package es.timebee.services;

import es.timebee.domain.dto.ActualizarEmpresaDto;
import es.timebee.domain.dto.EmpresaDto;
import es.timebee.domain.dto.FichajesDto;
import es.timebee.domain.dto.TrabajadorDto;

import java.util.List;

/**
 * Servicio que define las operaciones principales para gestionar empresas.
 * <p>
 * Aquí se orquestan las acciones clave: listar empresas, gestionar trabajadores,
 * manejar fichajes y actualizar datos. En resumen, ¡todo lo que una empresa
 * necesita para moverse ágilmente dentro del sistema!
 */
public interface EmpresaService {
    /**
     * Obtiene una lista completa de todas las empresas registradas.
     * <p>
     * Ideal para reportes, listados generales o administradores curiosos.
     *
     * @return lista de empresas en formato DTO
     */
    List<EmpresaDto> getAllEmpresas();

    /**
     * Recupera los detalles de una empresa específica usando su correo electrónico.
     * <p>
     * Un acceso directo a la ficha de la empresa.
     *
     * @param email correo de la empresa
     * @return DTO con la información de la empresa
     */
    EmpresaDto getEmpresaPorEmail(String email);

    /**
     * Obtiene todos los trabajadores asociados a una empresa.
     * <p>
     * Usado, por ejemplo, para mostrar plantillas o generar reportes internos.
     *
     * @param email correo de la empresa
     * @return lista de trabajadores en formato DTO
     */
    List<TrabajadorDto> getTrabajadoresEmpresaPorEmail(String email);

    /**
     * Obtiene todos los fichajes registrados por la empresa.
     * <p>
     * Aquí vive el registro de actividad, ideal para análisis y control horario.
     *
     * @param email correo de la empresa
     * @return lista de fichajes en formato DTO
     */
    List<FichajesDto> getFichajesEmpresaPorEmail(String email);

    /**
     * Da de alta a un nuevo trabajador en la empresa.
     * <p>
     * Añade talento fresco al equipo, validando que todo esté correcto.
     *
     * @param peticion datos del trabajador a registrar
     * @param emailEmpresa correo de la empresa que realiza la alta
     * @return true si se registra correctamente
     */
    Boolean altaTrabajador(TrabajadorDto peticion, String emailEmpresa);

    /**
     * Da de baja a un trabajador existente.
     * <p>
     * Gestiona las salidas de personal, asegurando coherencia de datos.
     *
     * @param emailTrabajador correo del trabajador a dar de baja
     * @param emailEmpresa correo de la empresa que solicita la baja
     * @return true si se da de baja correctamente
     */
    Boolean bajaTrabajador(String emailTrabajador, String emailEmpresa);

    /**
     * Actualiza la información de un trabajador específico.
     * <p>
     * Cambios, ajustes, correcciones… todo queda reflejado aquí.
     *
     * @param peticion datos actualizados del trabajador
     * @param emailEmpresa correo de la empresa que gestiona el cambio
     * @return true si la actualización fue exitosa
     */
    boolean actualizarTrabajador(TrabajadorDto peticion, String emailEmpresa);

    /**
     * Actualiza la información de la propia empresa.
     * <p>
     * Desde un nuevo logo hasta cambios en el nombre o detalles administrativos,
     * este método lo cubre todo.
     *
     * @param peticion datos actualizados de la empresa
     * @param emailEmpresa correo de la empresa que realiza la actualización
     * @return true si la actualización fue exitosa
     */
    Boolean actualizarEmpresa(ActualizarEmpresaDto peticion, String emailEmpresa);
}
