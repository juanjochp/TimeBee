package es.timebee.domain.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * {@code IdRequestDto} es un Data Transfer Object (DTO)
 * que encapsula simplemente un identificador único ({@code id}).
 * <p>
 * Este objeto es útil para operaciones genéricas donde solo se necesita
 * pasar un ID, como eliminar, consultar o modificar entidades por su identificador.
 */
@Data
public class IdRequestDto implements Serializable {

    private static  final long serialVersionUID = 2124109948331583435L;

    /** El identificador único asociado a la entidad objetivo. */
    private Long id;
}
