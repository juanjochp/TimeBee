package es.timebee.services;

import es.timebee.domain.dto.GeneroDto;

import java.util.List;

/**
 * Servicio que gestiona los géneros disponibles en el sistema.
 * <p>
 * Sencillo pero esencial: este servicio proporciona
 * la lista de géneros reconocidos, garantizando
 * que los formularios sean consistentes
 * y que los datos personales estén siempre bien categorizados.
 */
public interface GeneroService {

    /**
     * Lista todos los géneros disponibles.
     * <p>
     * Prepara y entrega una lista de DTOs,
     * lista para alimentar selectores, formularios
     * o cualquier parte de la aplicación que lo necesite.
     *
     * @return lista de DTOs representando los géneros
     */
    List<GeneroDto> listarGeneros();
}
