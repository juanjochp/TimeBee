package es.timebee.domain.repository;

import es.timebee.domain.entity.Empresa;
import es.timebee.domain.entity.Fichaje;
import es.timebee.domain.entity.Trabajador;
import es.timebee.domain.enumeration.FormaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * {@code EmpresaRepository} es la interfaz que gestiona el acceso a los datos
 * de las entidades {@link Empresa} usando Spring Data JPA.
 * <p>
 * Aquí definimos métodos personalizados para consultar, buscar y actualizar empresas,
 * así como obtener los trabajadores y fichajes asociados a ellas.
 * <p>
 * Gracias a extender de {@link JpaRepository}, ya contamos con muchas operaciones CRUD listas,
 * pero además agregamos consultas específicas usando {@code @Query} para cubrir
 * necesidades concretas de la aplicación TimeBee.
 */
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    /**
     * Busca una empresa por su correo electrónico, ignorando mayúsculas y minúsculas.
     *
     * @param email el correo electrónico.
     * @return un {@link Optional} con la empresa encontrada, si existe.
     */
    Optional<Empresa> findByEmail(String email);

    /**
     * Obtiene todos los trabajadores activos de una empresa dado su correo electrónico.
     *
     * @param email el correo electrónico de la empresa.
     * @return una lista de trabajadores activos.
     */
    @Query("select t from Trabajador t where upper(t.empresa.email) = upper(?1) and t.activo = true")
    List<Trabajador> findByTrabajadores_Empresa_EmailEqualsIgnoreCase(String email);

    /**
     * Obtiene todos los fichajes asociados a una empresa dado su correo electrónico.
     *
     * @param email el correo electrónico de la empresa.
     * @return una lista de fichajes.
     */
    @Query("select f from Fichaje f where upper(f.empresa.email) = upper(?1)")
    List<Fichaje> findByFichajes_Empresa_EmailEqualsIgnoreCase(String email);

    /**
     * Verifica si existe una empresa con el correo electrónico dado (ignorando mayúsculas/minúsculas).
     *
     * @param email el correo electrónico a comprobar.
     * @return {@code true} si existe, {@code false} si no.
     */
    boolean existsByEmailIgnoreCase(String email);

    /**
     * Verifica si existe una empresa con el CIF dado (ignorando mayúsculas/minúsculas).
     *
     * @param cif el CIF a comprobar.
     * @return {@code true} si existe, {@code false} si no.
     */
    boolean existsByCifIgnoreCase(String cif);

    /**
     * Actualiza los datos personales de una empresa (sin cambiar la contraseña),
     * usando el correo electrónico como identificador.
     *
     * @param email        el nuevo correo.
     * @param nombre       el nuevo nombre.
     * @param formaJuridica la nueva forma jurídica.
     * @param cif          el nuevo CIF.
     * @param direccion    la nueva dirección.
     * @param telefono     el nuevo teléfono.
     * @param email1       el correo actual usado para buscar la empresa.
     */
    @Transactional
    @Modifying
    @Query("""
            update Empresa e set e.email = ?1, e.nombre = ?2, e.formaJuridica = ?3, e.cif = ?4, e.direccion = ?5, e.telefono = ?6
            where upper(e.email) = upper(?7)""")
    void updateDatosPersonalesEmpresa(String email, String nombre, FormaJuridica formaJuridica, String cif, String direccion, int telefono, String email1);

    /**
     * Actualiza los datos personales de una empresa, incluyendo la contraseña,
     * usando el correo electrónico como identificador.
     *
     * @param password     la nueva contraseña.
     * @param email        el nuevo correo.
     * @param nombre       el nuevo nombre.
     * @param formaJuridica la nueva forma jurídica.
     * @param cif          el nuevo CIF.
     * @param direccion    la nueva dirección.
     * @param telefono     el nuevo teléfono.
     * @param email1       el correo actual usado para buscar la empresa.
     */
    @Transactional
    @Modifying
    @Query("""
            update Empresa e set e.password = ?1, e.email = ?2, e.nombre = ?3, e.formaJuridica = ?4, e.cif = ?5, e.direccion = ?6, e.telefono = ?7
            where upper(e.email) = upper(?8)""")
    void updateDatosPersonalesPasswordEmpresa(String password, String email, String nombre, FormaJuridica formaJuridica, String cif, String direccion, int telefono, String email1);
}
