package es.timebee.domain.entity;

import es.timebee.domain.constantes.BaseDeDatos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * {@code Fichaje} es una entidad JPA que representa un registro
 * de entrada o salida de un trabajador en el sistema TimeBee.
 * <p>
 * Este objeto almacena cuándo un trabajador inicia y finaliza su jornada
 * o sesión laboral, y está asociado tanto al trabajador como a la empresa.
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = BaseDeDatos.Tablas.FICHAJE)
public class Fichaje {

    /** El identificador único del fichaje (clave primaria, autogenerada). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = BaseDeDatos.Columnas.ID, nullable = false)
    private Long id;

    /** El trabajador al que pertenece este fichaje. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = BaseDeDatos.Columnas.TRABAJADOR_ID, nullable = false)
    private Trabajador trabajador;

    /** La empresa en la que se realiza el fichaje. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = BaseDeDatos.Columnas.EMPRESA_ID, nullable = false)
    private Empresa empresa;

    /** La fecha y hora en que comenzó el fichaje (entrada). */
    @Column(name = BaseDeDatos.Columnas.FECHA_INICIO, nullable = false)
    private LocalDateTime fechaInicio;

    /** La fecha y hora en que finalizó el fichaje (salida). Puede ser nula si aún está abierto. */
    @Column(name = BaseDeDatos.Columnas.FECHA_FIN)
    private LocalDateTime fechaFin;

}
