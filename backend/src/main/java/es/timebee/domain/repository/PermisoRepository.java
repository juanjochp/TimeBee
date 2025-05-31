package es.timebee.domain.repository;

import es.timebee.domain.entity.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * {@code PermisoRepository} es la interfaz que se encarga de manejar el acceso a los datos
 * relacionados con las entidades {@link Permiso} usando Spring Data JPA.
 * <p>
 * Al extender de {@link JpaRepository}, hereda automáticamente todas las operaciones básicas
 * de CRUD (crear, leer, actualizar, eliminar) para los permisos,
 * permitiendo trabajar de forma rápida y sin escribir consultas manuales para lo básico.
 * <p>
 * Además, define métodos personalizados para obtener permisos
 * según el trabajador o la empresa asociada, algo fundamental para TimeBee.
 */
public interface PermisoRepository extends JpaRepository<Permiso, Long> {

    /**
     * Obtiene todos los permisos de los trabajadores que pertenecen a una empresa específica.
     *
     * @param empresaId el id de la empresa.
     * @return una lista de permisos asociados a esa empresa.
     */
    List<Permiso> findByTrabajador_Empresa_Id(Long empresaId);

    /**
     * Obtiene todos los permisos de un trabajador específico.
     *
     * @param trabajadorId el id del trabajador.
     * @return una lista de permisos asociados a ese trabajador.
     */
    List<Permiso> findByTrabajador_Id(Long trabajadorId);
}
