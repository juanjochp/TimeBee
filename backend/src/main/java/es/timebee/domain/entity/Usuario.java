package es.timebee.domain.entity;

import es.timebee.domain.constantes.BaseDeDatos;
import es.timebee.domain.constantes.Numeros;
import es.timebee.domain.enumeration.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * {@code Usuario} es una clase abstracta marcada como {@code @MappedSuperclass}
 * que representa los atributos comunes a todos los tipos de usuarios del sistema TimeBee,
 * como {@link Trabajador} y {@link Empresa}.
 * <p>
 * Esta clase define propiedades compartidas como email, contraseña, rol y estado de activación,
 * que son reutilizadas por las entidades hijas.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class Usuario {

    /** El correo electrónico del usuario (único, máximo 255 caracteres, obligatorio). */
    @Size(max = Numeros.DOSCIENTOSCINCUENTAYCINCO)
    @Column(name = BaseDeDatos.Columnas.EMAIL , unique = true, nullable = false)
    private String email;

    /** La contraseña encriptada del usuario (obligatoria). */
    @Column(nullable = false)
    private String password;

    /** El rol asignado al usuario (enum {@code Rol}, obligatorio). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    /** Indica si el usuario está activo (true) o desactivado (false). */
    @Column(nullable = false)
    private boolean activo = true;

}
