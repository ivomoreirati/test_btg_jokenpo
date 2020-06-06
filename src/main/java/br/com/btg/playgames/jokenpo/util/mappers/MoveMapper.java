package br.com.btg.playgames.jokenpo.util.mappers;

import br.com.btg.playgames.jokenpo.presenters.MoveResponseDTO;
import br.com.btg.playgames.jokenpo.model.Move;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoveMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveMapper.class);

    private static final ModelMapper MAPPER = new ModelMapper();

    public static MoveResponseDTO entityToResponse(Move entity) {
        LOGGER.debug("Converting: entity object to response object");
        MoveResponseDTO response = MAPPER.map(entity, MoveResponseDTO.class);
        response.setMovement(entity.getMovementConstants());
        return response;
    }

}
