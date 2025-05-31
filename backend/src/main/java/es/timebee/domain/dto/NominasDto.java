package es.timebee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * {@code NominasDto} es un Data Transfer Object (DTO)
 * que representa una nómina completa dentro del sistema TimeBee.
 * <p>
 * Este objeto contiene toda la información relevante de una nómina,
 * incluyendo sus metadatos, contenido en PDF, relación con el trabajador
 * y la empresa, y detalles sobre cuándo fue subida.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class NominasDto implements Serializable {

    private static final long serialVersionUID = -4748543874552167770L;

    /** El identificador único de la nómina. */
    private Long id;

    /** El nombre del archivo de la nómina (p.ej. “nomina-marzo.pdf”). */
    private String nombreArchivo;

    /** El contenido binario del archivo PDF. */
    private byte[] archivoPdf;

    /** El tipo de contenido (MIME type), p.ej. “application/pdf”. */
    private String contentType;

    /** El tamaño del archivo, expresado en bytes. */
    private Long tamano;

    /** La fecha y hora exacta en que se subió la nómina al sistema. */
    private LocalDateTime fechaSubida;

    /** El periodo al que corresponde la nómina (p.ej. marzo 2025). */
    private LocalDate periodo;

    /** El trabajador asociado a esta nómina (DTO embebido). */
    private TrabajadorDto trabajador;

    /** La empresa asociada a esta nómina (DTO embebido). */
    private EmpresaDto empresa;

    /**
     * Constructor vacío requerido por frameworks de serialización/deserialización.
     */
    public NominasDto() {
        //Constructor
    }
}
