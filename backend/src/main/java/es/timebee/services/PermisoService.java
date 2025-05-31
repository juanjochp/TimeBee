package es.timebee.services;

import es.timebee.domain.dto.*;

import java.util.List;

/**
 * Servicio encargado de gestionar los permisos laborales.
 * <p>
 * Desde listar tipos de permisos hasta solicitar, editar, validar o eliminar solicitudes,
 * este servicio es el corazón del módulo de permisos.
 * Un lugar donde trabajadores y empresas interactúan, con reglas claras.
 */
public interface PermisoService {

    /**
     * Lista todos los tipos de permisos disponibles.
     * <p>
     * Ideal para desplegar en formularios, configuraciones
     * o simplemente para que los usuarios sepan qué opciones tienen.
     *
     * @return lista de tipos de permiso
     */
    List<TipoPermisoDto> listarTiposPermiso();

    /**
     * Permite a un trabajador solicitar un permiso.
     *
     * @param dto datos del permiso solicitado
     * @param emailTrabajador correo del trabajador solicitante
     * @return true si se guarda correctamente
     */
    Boolean solicitarPermiso(PermisoSolicitarDto dto, String emailTrabajador);

    /**
     * Permite a un trabajador editar su propia solicitud de permiso.
     *
     * @param dto datos editados del permiso
     * @param emailTrabajador correo del trabajador que edita
     * @return true si la edición fue exitosa
     */
    Boolean editarPermiso(PermisoEditarDto dto, String emailTrabajador);

    /**
     * Permite a una empresa validar (aprobar o rechazar) un permiso.
     *
     * @param dto datos de validación
     * @param emailEmpresa correo de la empresa que valida
     * @return true si la validación fue exitosa
     */
    Boolean validarPermiso(PermisoValidarDto dto, String emailEmpresa);

    /**
     * Permite a un trabajador eliminar su propia solicitud de permiso.
     *
     * @param id id del permiso
     * @param emailTrabajador correo del trabajador
     * @return true si se elimina correctamente
     */
    Boolean eliminarPermiso(Long id, String emailTrabajador);

    /**
     * Obtiene todos los permisos de una empresa.
     * <p>
     * Útil para informes, paneles de gestión y revisiones.
     *
     * @param emailEmpresa correo de la empresa
     * @return lista de permisos
     */
    List<PermisosDto> obtenerPermisosPorEmpresaId(String emailEmpresa);

    /**
     * Obtiene todos los permisos asociados a un trabajador.
     *
     * @param emailTrabajador correo del trabajador
     * @return lista de permisos
     */
    List<PermisosDto> obtenerPermisosPorTrabajadorId(String emailTrabajador);

}
