package es.timebee.domain.entity;

import es.timebee.domain.constantes.BaseDeDatos;
import es.timebee.domain.constantes.Numeros;
import es.timebee.domain.enumeration.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code Trabajador} es una entidad JPA que representa a un trabajador
 * registrado en el sistema TimeBee.
 * <p>
 * Este objeto almacena información personal, laboral y de contacto del trabajador,
 * así como las relaciones con su empresa, sus fichajes, permisos y nóminas.
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = BaseDeDatos.Tablas.TRABAJADOR)
public class Trabajador extends Usuario {

    /** El identificador único del trabajador (clave primaria, autogenerada). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = BaseDeDatos.Columnas.ID, nullable = false)
    private Long id;

    /** El nombre del trabajador. */
    @Size(max = Numeros.DOSCIENTOSCINCUENTAYCINCO)
    @Column(name = BaseDeDatos.Columnas.NOMBRE, nullable = false)
    private String nombre;

    /** Los apellidos del trabajador. */
    @Size(max = Numeros.DOSCIENTOSCINCUENTAYCINCO)
    @Column(name = BaseDeDatos.Columnas.APELLIDOS, nullable = false)
    private String apellidos;

    /** El número de DNI del trabajador. */
    @Size(max = Numeros.NUEVE)
    @Column(name = BaseDeDatos.Columnas.DNI, nullable = false)
    private String dni;

    /** El número NAF (Número de Afiliación a la Seguridad Social) del trabajador. */
    @Size(max = Numeros.DOCE)
    @Column(name = BaseDeDatos.Columnas.NAF, nullable = false)
    private String naf;

    /** El género del trabajador (enum {@code Genero}, obligatorio). */
    @Enumerated(EnumType.STRING)
    @Column(name = BaseDeDatos.Columnas.GENERO, nullable = false)
    @NotNull
    private Genero genero;

    /** La fecha de nacimiento del trabajador. */
    @Column(name = BaseDeDatos.Columnas.FECHA_NACIMIENTO, nullable = false)
    private LocalDate fechaNacimiento;

    /** La fecha de antigüedad laboral del trabajador en la empresa. */
    @Column(name = BaseDeDatos.Columnas.FECHA_ANTIGUEDAD, nullable = false)
    private LocalDate fechaAntiguedad;

    /** La categoría laboral del trabajador. */
    @Column(name = BaseDeDatos.Columnas.CATEGORIA, nullable = false)
    @Size(max = Numeros.DOSCIENTOSCINCUENTAYCINCO)
    private String categoria;

    /** El número IBAN de la cuenta bancaria del trabajador. */
    @Column(name = BaseDeDatos.Columnas.IBAN, nullable = false)
    @Size(max = Numeros.VEINTICUATRO)
    private String iban;

    /** El número de teléfono del trabajador (hasta 9 dígitos). */
    @Digits(integer = Numeros.NUEVE, fraction = 0)
    @Column(name = BaseDeDatos.Columnas.TELEFONO)
    private int telefono;

    /** La empresa a la que pertenece este trabajador. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = BaseDeDatos.Columnas.EMPRESA_ID, nullable = false)
    private Empresa empresa;

    /** Lista de fichajes realizados por este trabajador. */
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fichaje> fichajes = new ArrayList<>();

    /** Lista de permisos solicitados por este trabajador. */
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Permiso> permisos = new ArrayList<>();

    /** Lista de nóminas asociadas a este trabajador. */
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nomina> nominas = new ArrayList<>();
}
