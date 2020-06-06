package br.com.btg.playgames.jokenpo.util.mappers;

import br.com.btg.playgames.jokenpo.parameters.PlayerRequestDTO;
import br.com.btg.playgames.jokenpo.presenters.PlayerResponseDTO;
import br.com.btg.playgames.jokenpo.model.Player;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerMapper.class);

    private static final ModelMapper MAPPER = new ModelMapper();

    public static Player requestToEntity(PlayerRequestDTO playerRequestDTO){
        LOGGER.debug("Converting: request object to entity object");
        return MAPPER.map(playerRequestDTO, Player.class);
    }

    public static PlayerResponseDTO entityToResponse(Player entity) {
        LOGGER.debug("Converting: entity object to response object");
        return MAPPER.map(entity, PlayerResponseDTO.class);
    }

}
