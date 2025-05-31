package es.timebee.domain.repository;

import es.timebee.domain.entity.Fichaje;
import es.timebee.domain.entity.Trabajador;
import es.timebee.domain.enumeration.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * {@code TrabajadorRepository} es la interfaz que permite gestionar el acceso
 * a los datos de la entidad {@link Trabajador} usando Spring Data JPA.
 * <p>
 * Hereda de {@link JpaRepository}, lo que significa que ya contamos
 * con todas las operaciones básicas (guardar, buscar, eliminar, actualizar).
 * Pero además, aquí añadimos consultas personalizadas para cubrir
 * las necesidades específicas del proyecto TimeBee.
 */
public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {

    /**
     * Busca todos los fichajes asociados a un trabajador por su email (ignorando mayúsculas/minúsculas).
     *
     * @param email el correo electrónico del trabajador.
     * @return una lista de fichajes relacionados con ese trabajador.
     */
    @Query("select f from Fichaje f where upper(f.trabajador.email) = upper(?1)")
    List<Fichaje> findByFichajes_Trabajador_EmailEqualsIgnoreCase(String email);

    /**
     * Actualiza los datos personales de un trabajador desde la empresa.
     *
     * @param email           nuevo correo electrónico.
     * @param password        nueva contraseña.
     * @param activo          estado activo o inactivo.
     * @param nombre          nuevo nombre.
     * @param apellidos       nuevos apellidos.
     * @param dni             nuevo DNI.
     * @param naf             nuevo número de afiliación.
     * @param genero          nuevo género.
     * @param fechaNacimiento nueva fecha de nacimiento.
     * @param fechaAntiguedad nueva fecha de antigüedad.
     * @param categoria       nueva categoría.
     * @param iban            nuevo IBAN.
     * @param telefono        nuevo teléfono.
     * @param id              identificador del trabajador.
     */
    @Transactional
    @Modifying
    @Query("""
            update Trabajador t set t.email = ?1, t.password = ?2, t.activo = ?3, t.nombre = ?4, t.apellidos = ?5, t.dni = ?6, t.naf = ?7, t.genero = ?8, t.fechaNacimiento = ?9, t.fechaAntiguedad = ?10, t.categoria = ?11, t.iban = ?12, t.telefono = ?13
            where t.id = ?14""")
    void updateDatosPersonalesTrabajadorDesdeEmpresa(String email, String password, boolean activo, String nombre, String apellidos, String dni, String naf, Genero genero, LocalDate fechaNacimiento, LocalDate fechaAntiguedad, String categoria, String iban, int telefono, Long id);

    /**
     * Actualiza los datos personales de un trabajador (sin cambiar contraseña).
     *
     * @param nuevoEmail      nuevo correo electrónico.
     * @param nombre          nuevo nombre.
     * @param apellidos       nuevos apellidos.
     * @param genero          nuevo género.
     * @param dni             nuevo DNI.
     * @param iban            nuevo IBAN.
     * @param fechaNacimiento nueva fecha de nacimiento.
     * @param telefono        nuevo teléfono.
     * @param emailActual     correo electrónico actual (para localizar al trabajador).
     */
    @Transactional
    @Modifying
    @Query("UPDATE Trabajador t SET t.email = :nuevoEmail, t.nombre = :nombre, t.apellidos = :apellidos, t.genero = :genero," +
            " t.dni = :dni, t.iban = :iban, t.fechaNacimiento = :fechaNacimiento, t.telefono = :telefono WHERE t.email = :emailActual")
    void updateDatosPersonalesTrabajador(
            String nuevoEmail,
            String nombre,
            String apellidos,
            Genero genero,
            String dni,
            String iban,
            LocalDate fechaNacimiento,
            String telefono,
            String emailActual
    );

    /**
     * Actualiza los datos personales y la contraseña del trabajador.
     *
     * @param nuevaPassword   nueva contraseña.
     * @param nuevoEmail      nuevo correo electrónico.
     * @param nombre          nuevo nombre.
     * @param apellidos       nuevos apellidos.
     * @param genero          nuevo género.
     * @param dni             nuevo DNI.
     * @param iban            nuevo IBAN.
     * @param fechaNacimiento nueva fecha de nacimiento.
     * @param telefono        nuevo teléfono.
     * @param emailActual     correo electrónico actual (para localizar al trabajador).
     */
    @Transactional
    @Modifying
    @Query("UPDATE Trabajador t SET  t.password = :nuevaPassword, t.email = :nuevoEmail, t.nombre = :nombre, t.apellidos = :apellidos," +
            " t.genero = :genero, t.dni = :dni, t.iban = :iban, t.fechaNacimiento = :fechaNacimiento, t.telefono = :telefono " +
            "WHERE t.email = :emailActual ")
    void updateDatosPersonalesPasswordTrabajador(
            String nuevaPassword,
            String nuevoEmail,
            String nombre,
            String apellidos,
            Genero genero,
            String dni,
            String iban,
            LocalDate fechaNacimiento,
            String telefono,
            String emailActual
    );

    /**
     * Verifica si existe un trabajador con el correo electrónico dado (ignorando mayúsculas/minúsculas).
     *
     * @param email el correo electrónico a verificar.
     * @return {@code true} si existe, {@code false} si no.
     */
    boolean existsByEmailIgnoreCase(String email);

    /**
     * Verifica si existe un trabajador con el DNI dado (ignorando mayúsculas/minúsculas).
     *
     * @param dni el DNI a verificar.
     * @return {@code true} si existe, {@code false} si no.
     */
    boolean existsByDniIgnoreCase(String dni);

    /**
     * Busca un trabajador por su correo electrónico (ignorando mayúsculas/minúsculas).
     *
     * @param trabajadorEmail el correo electrónico del trabajador.
     * @return un {@link Optional} que contiene el trabajador si existe.
     */
    Optional<Trabajador> findByEmailIgnoreCase(String trabajadorEmail);

    /**
     * Inactiva (desactiva) a un trabajador cambiando su estado a falso.
     *
     * @param email el correo electrónico del trabajador a desactivar.
     */
    @Transactional
    @Modifying
    @Query("update Trabajador t set t.activo = false where upper(t.email) = upper(?1)")
    void inactivarTrabajador(String email);
}
