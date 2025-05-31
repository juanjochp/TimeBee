package es.timebee.services.impl;

import es.timebee.domain.dto.GeneroDto;
import es.timebee.domain.enumeration.Genero;
import es.timebee.services.GeneroService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio dedicado a listar los géneros disponibles.
 * <p>
 * Aunque sencillo a primera vista, este servicio es clave:
 * toma los valores del enum {@link Genero}, los transforma en bonitos DTOs
 * y los entrega listos para usarse en formularios, listados o validaciones.
 *
 * Un toque rápido. Una conversión elegante. Sin vueltas innecesarias.
 */
@Service
public class GeneroServiceImpl implements GeneroService {

    @Override
    public List<GeneroDto> listarGeneros() {
        return Arrays.stream(Genero.values())
                .map(g -> new GeneroDto(
                        g.getId(),
                        g.getNombre(),
                        g.name()
                ))
                .collect(Collectors.toList());
    }
}
