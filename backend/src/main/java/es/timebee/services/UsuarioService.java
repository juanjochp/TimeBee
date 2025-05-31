package es.timebee.services;

import es.timebee.domain.dto.EmpresaDto;

/**
 * Servicio que gestiona operaciones relacionadas con usuarios empresa.
 * <p>
 * Actualmente, su función principal es permitir el alta de nuevas empresas:
 * validar que no existan duplicados, registrar los datos
 * y dejarlas listas para empezar a trabajar en el sistema.
 */
public interface UsuarioService {

    /**
     * Da de alta una nueva empresa en el sistema.
     * <p>
     * Comprueba que no haya duplicados por correo o CIF,
     * y registra la empresa asignándole su rol y estado inicial.
     *
     * @param peticion DTO con los datos de la empresa
     * @return true si el alta fue exitosa
     */
    Boolean altaEmpresa(EmpresaDto peticion);
}
