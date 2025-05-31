package es.timebee.services.impl;

import es.timebee.domain.dto.FormaJuridicaDto;
import es.timebee.domain.enumeration.FormaJuridica;
import es.timebee.services.FormaJuridicaService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que se encarga — con ligereza pero mucha utilidad —
 * de listar todas las formas jurídicas disponibles.
 * <p>
 * Aquí no hay grandes dramas ni complicaciones: solo tomar los enums,
 * envolverlos en DTOs simpáticos y devolverlos listos para usar.
 * Es simple, es directo, ¡y funciona!
 */
@Service
public class FormaJuridicaServiceImpl implements FormaJuridicaService {

    /**
     * Lista todas las formas jurídicas disponibles en el sistema.
     * <p>
     * Recorre cada valor del enum {@link FormaJuridica}, lo transforma
     * en un {@link FormaJuridicaDto} (con su id, su nombre y su clave),
     * y los junta en una lista para que quien lo necesite pueda consultarlo.
     *
     * ¿El resultado? Una lista fresca, completa, y perfecta para desplegar
     * en formularios, catálogos o lo que imagines.
     *
     * @return lista de DTOs de formas jurídicas
     */
    @Override
    public List<FormaJuridicaDto> listarFormasJuridicas() {
        return Arrays.stream(FormaJuridica.values())
                .map(fj -> new FormaJuridicaDto(
                        fj.getId(),
                        fj.getNombre(),
                        fj.name()
                ))
                .collect(Collectors.toList());
    }
}
