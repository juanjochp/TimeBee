package es.timebee.domain.entity;

import es.timebee.domain.constantes.BaseDeDatos;
import es.timebee.domain.enumeration.EstadoPermiso;
import es.timebee.domain.enumeration.TipoPermiso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * {@code Permiso} es una entidad JPA que representa una solicitud de permiso
 * realizada por un trabajador dentro del sistema TimeBee.
 * <p>
 * Este objeto almacena detalles como el tipo de permiso solicitado (p.ej. vacaciones, médico),
 * la fecha, las horas pedidas, el trabajador asociado y el estado actual
 * de la solicitud (pendiente, aprobado, rechazado).
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = BaseDeDatos.Tablas.PERMISO)
public class Permiso {

    /** El identificador único del permiso (clave primaria, autogenerada). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = BaseDeDatos.Columnas.ID, nullable = false)
    private Long id;

    /** El tipo de permiso solicitado (enum {@code TipoPermiso}, obligatorio). */
    @Enumerated(EnumType.STRING)
    @Column(name = BaseDeDatos.Columnas.TIPOPERMISO, nullable = false)
    private TipoPermiso permiso;

    /** El trabajador que ha solicitado este permiso. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = BaseDeDatos.Columnas.TRABAJADOR_ID, nullable = false)
    private Trabajador trabajador;

    /** La fecha para la cual se solicita el permiso. */
    @Column(name = BaseDeDatos.Columnas.FECHA, nullable = false)
    private LocalDate fecha;

    /** La cantidad de horas solicitadas para el permiso. */
    @Column(name = BaseDeDatos.Columnas.HORA, nullable = false)
    private float hora;

    /** El estado actual del permiso (SOLICITADO, APROBADO, RECHAZADO). */
    @Enumerated(EnumType.STRING)
    @Column(name = BaseDeDatos.Columnas.ESTADO, nullable = false)
    private EstadoPermiso estado = EstadoPermiso.SOLICITADO;
}
