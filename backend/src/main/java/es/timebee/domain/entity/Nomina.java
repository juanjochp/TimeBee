package es.timebee.domain.entity;

import es.timebee.domain.constantes.BaseDeDatos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * {@code Nomina} es una entidad JPA que representa una nómina generada
 * para un trabajador dentro del sistema TimeBee.
 * <p>
 * Este objeto almacena toda la información relevante de una nómina,
 * incluyendo el archivo PDF, su tamaño, la fecha de subida, el periodo
 * al que corresponde y las relaciones con el trabajador y la empresa.
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = BaseDeDatos.Tablas.NOMINA)
public class Nomina {

    /** El identificador único de la nómina (clave primaria, autogenerada). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = BaseDeDatos.Columnas.ID, nullable = false)
    private Long id;

    /** El nombre del archivo de la nómina (p.ej. “nomina-abril.pdf”). */
    @Column(name = BaseDeDatos.Columnas.NOMBRE_ARCHIVO, nullable = false)
    private String nombreArchivo;

    /** El contenido binario del archivo PDF de la nómina (almacenado como LONGBLOB). */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = BaseDeDatos.Columnas.ARCHIVO_PDF, nullable = false, columnDefinition = "LONGBLOB")
    private byte[] archivoPdf;

    /** El tipo de contenido del archivo (MIME type, p.ej. “application/pdf”). */
    @Column(name = BaseDeDatos.Columnas.CONTENT_TYPE, nullable = false)
    private String contentType;

    /** El tamaño del archivo en bytes. */
    @Column(name = BaseDeDatos.Columnas.TAMANO, nullable = false)
    private Long tamano;

    /** La fecha y hora en que se subió la nómina al sistema (generada automáticamente). */
    @CreationTimestamp
    @Column(name = BaseDeDatos.Columnas.FECHA_SUBIDA, nullable = false)
    private LocalDateTime fechaSubida;

    /** El periodo al que corresponde la nómina (p.ej. abril 2025). */
    @Column(name = BaseDeDatos.Columnas.PERIODO, nullable = false)
    private LocalDate periodo;

    /** El trabajador al que pertenece esta nómina. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = BaseDeDatos.Columnas.TRABAJADOR_ID, nullable = false)
    private Trabajador trabajador;

    /** La empresa que emitió esta nómina. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = BaseDeDatos.Columnas.EMPRESA_ID, nullable = false)
    private Empresa empresa;

}
