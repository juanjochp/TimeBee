package es.timebee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code NominaDownloadDto} es un Data Transfer Object (DTO)
 * que encapsula los datos necesarios para descargar una nómina
 * en el sistema TimeBee.
 * <p>
 * Este objeto transporta el nombre del archivo, su tipo de contenido
 * (MIME type) y el contenido binario del PDF para que el cliente pueda
 * generar la descarga correctamente.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NominaDownloadDto {

    /** El nombre del archivo de la nómina (p.ej. “nomina-abril.pdf”). */
    private String nombreArchivo;

    /** El tipo de contenido del archivo (MIME type, p.ej. “application/pdf”). */
    private String contentType;

    /** El contenido binario del archivo PDF. */
    private byte[] archivoPdf;
}
