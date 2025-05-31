package es.timebee.domain.repository;

import es.timebee.domain.entity.Nomina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * {@code NominaRepository} es la interfaz responsable de gestionar el acceso a los datos
 * relacionados con las entidades {@link Nomina} usando Spring Data JPA.
 * <p>
 * Gracias a extender de {@link JpaRepository}, ya contamos automáticamente con las operaciones CRUD básicas
 * (crear, leer, actualizar, eliminar) sobre las nóminas, sin necesidad de escribirlas manualmente.
 * <p>
 * Además, definimos métodos específicos para consultar nóminas por trabajador,
 * cubriendo así las principales necesidades de la aplicación TimeBee.
 */
public interface NominaRepository extends JpaRepository<Nomina, Long> {

    /**
     * Busca todas las nóminas de un trabajador específico, usando su identificador.
     *
     * @param trabajadorId el id del trabajador.
     * @return una lista con todas las nóminas del trabajador.
     */
    List<Nomina> findByTrabajador_Id(Long trabajadorId);

    /**
     * Busca todas las nóminas de un trabajador específico, usando su correo electrónico.
     *
     * @param trabajadorEmail el correo electrónico del trabajador.
     * @return una lista con todas las nóminas del trabajador.
     */
    List<Nomina> findByTrabajador_Email(String trabajadorEmail);
}

