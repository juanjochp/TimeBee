package es.timebee.domain.repository;

import es.timebee.domain.entity.Fichaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@code FichajeRepository} es la interfaz que gestiona el acceso a los datos
 * relacionados con la entidad {@link Fichaje} usando Spring Data JPA.
 * <p>
 * A través de esta interfaz, podemos consultar y guardar fichajes en la base de datos
 * sin necesidad de escribir código SQL manual en la lógica del servicio.
 * <p>
 * Además de las operaciones CRUD básicas que hereda de {@link JpaRepository},
 * aquí se definen métodos específicos para las necesidades particulares de TimeBee.
 */
public interface FichajeRepository extends JpaRepository<Fichaje, Long> {

    /**
     * Busca todos los fichajes abiertos (sin fecha de fin) para un trabajador específico,
     * ordenados por fecha de inicio de forma ascendente.
     *
     * @param trabajadorId el identificador del trabajador.
     * @return una lista de fichajes abiertos.
     */
    @Query("select f from Fichaje f where f.trabajador.id = ?1 and f.fechaFin is null order by f.fechaInicio")
    List<Fichaje> findByTrabajador_IdAndFechaFinIsNullOrderByFechaInicioAsc(Long trabajadorId);

    /**
     * Guarda un nuevo fichaje sin fecha de salida (fichaje abierto) usando una consulta nativa.
     *
     * @param trabajadorId el identificador del trabajador.
     * @param empresaId el identificador de la empresa.
     * @param fechaInicio la fecha y hora de inicio del fichaje.
     */
    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO fichaje (trabajador_id, empresa_id, fecha_inicio) " +
                    "VALUES (:trabajadorId, :empresaId, :fechaInicio)",
            nativeQuery = true
    )
    void guardarFichajeSinCerrar(
            @Param("trabajadorId") Long trabajadorId,
            @Param("empresaId") Long empresaId,
            @Param("fechaInicio") LocalDateTime fechaInicio
    );
}
