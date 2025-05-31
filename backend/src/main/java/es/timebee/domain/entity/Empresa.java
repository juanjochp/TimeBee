package es.timebee.domain.entity;

import es.timebee.domain.constantes.BaseDeDatos;
import es.timebee.domain.constantes.Numeros;
import es.timebee.domain.enumeration.FormaJuridica;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Empresa} es una entidad JPA que representa a una empresa registrada
 * en el sistema TimeBee.
 * <p>
 * Esta entidad hereda de {@code Usuario} e incluye información clave como nombre,
 * forma jurídica, CIF, dirección y teléfono, así como las relaciones
 * con sus trabajadores, fichajes y nóminas.
 */
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = BaseDeDatos.Tablas.EMPRESA)
public class Empresa extends Usuario{

    /** El identificador único de la empresa (clave primaria, autogenerada). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = BaseDeDatos.Columnas.ID, nullable = false)
    private Long id;

    /** El nombre de la empresa (máximo 255 caracteres, obligatorio). */
    @Size(max = Numeros.DOSCIENTOSCINCUENTAYCINCO)
    @Column(name = BaseDeDatos.Columnas.NOMBRE, nullable = false)
    private String nombre;

    /** La forma jurídica de la empresa (enum {@code FormaJuridica}, obligatorio). */
    @Enumerated(EnumType.STRING)
    @Column(name = BaseDeDatos.Columnas.FORMA_JURIDICA, nullable = false)
    private FormaJuridica formaJuridica;

    /** El CIF de la empresa (máximo 9 caracteres, obligatorio). */
    @Size(max = Numeros.NUEVE)
    @Column(name = BaseDeDatos.Columnas.CIF, nullable = false)
    private String cif;

    /** La dirección física de la empresa (máximo 255 caracteres, obligatorio). */
    @Size(max = Numeros.DOSCIENTOSCINCUENTAYCINCO)
    @Column(name = BaseDeDatos.Columnas.DIRECCION, nullable = false)
    private String direccion;

    /** El número de teléfono de la empresa (máximo 9 dígitos). */
    @Digits(integer = 9, fraction = 0)
    @Column(name = BaseDeDatos.Columnas.TELEFONO)
    private int telefono;

    /** Lista de trabajadores asociados a esta empresa. */
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Trabajador> trabajadores;

    /** Lista de fichajes realizados dentro de la empresa. */
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fichaje> fichajes = new ArrayList<>();

    /** Lista de nóminas generadas por la empresa. */
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nomina> nominas = new ArrayList<>();
}
