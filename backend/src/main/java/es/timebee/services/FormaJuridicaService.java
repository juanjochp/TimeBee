package es.timebee.services;

import es.timebee.domain.dto.FormaJuridicaDto;

import java.util.List;

/**
 * Servicio que gestiona las formas jurídicas.
 * <p>
 * Aunque sencillo, este servicio es esencial:
 * proporciona la lista de formas jurídicas reconocidas por el sistema,
 * permitiendo que empresas se clasifiquen correctamente
 * y que los formularios se llenen con datos validados.
 */
public interface FormaJuridicaService {

    /**
     * Lista todas las formas jurídicas disponibles.
     * <p>
     * Enumera, empaqueta en DTOs y entrega,
     * listo para alimentar menús desplegables,
     * validaciones o cualquier interfaz que lo requiera.
     *
     * @return lista de DTOs representando las formas jurídicas
     */
    List<FormaJuridicaDto> listarFormasJuridicas();
}
