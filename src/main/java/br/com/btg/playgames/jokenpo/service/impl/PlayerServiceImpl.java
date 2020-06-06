package br.com.btg.playgames.jokenpo.service.impl;

import br.com.btg.playgames.jokenpo.parameters.PlayerRequestDTO;
import br.com.btg.playgames.jokenpo.presenters.PlayerResponseDTO;
import br.com.btg.playgames.jokenpo.model.Player;
import br.com.btg.playgames.jokenpo.util.mappers.PlayerMapper;
import br.com.btg.playgames.jokenpo.constants.MessageException;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;
import br.com.btg.playgames.jokenpo.repository.PlayerRepository;
import br.com.btg.playgames.jokenpo.service.PlayerService;
import br.com.btg.playgames.jokenpo.util.PlayerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private final PlayerRepository playerRepository;
    private final MoveServiceImpl moveService;

    @Autowired
    public PlayerServiceImpl(final PlayerRepository playerRepository, final MoveServiceImpl moveService){
        this.playerRepository = playerRepository;
        this.moveService = moveService;
    }

    public PlayerResponseDTO insert(final PlayerRequestDTO player) throws JokenpoException {
        if(this.verifyIfAlreadyExistsByName(player.getName())){
            LOGGER.error("Player already exists");
            throw new JokenpoException(MessageException.PLAYER_ALREADY_EXISTS);
        }
        LOGGER.debug("Insert new player - Request: " + player.toString());
        var entity = PlayerMapper.requestToEntity(player);
        LOGGER.debug("Inserting player");
        entity = this.playerRepository.save(entity);
        LOGGER.debug("Creating response object");
        return PlayerMapper.entityToResponse(entity);
    }

    public List<PlayerResponseDTO> getAll() throws JokenpoException {
        LOGGER.debug("Finding all players");
        final var entityList = this.playerRepository.findAll();
        List<PlayerResponseDTO> response = new ArrayList<>();
        entityList.forEach(elem -> response.add(PlayerMapper.entityToResponse(elem)));
        LOGGER.debug("Players filtered");
        return response;
    }

    public Player getEntityByName(final String name) throws JokenpoException {
        LOGGER.debug("Finding player by name : {}", name);
        return this.playerRepository.findByName(name);
    }

    public List<PlayerResponseDTO> deleteByName(final String name) throws JokenpoException {
        if(StringUtils.isEmpty(name)){
            LOGGER.error("Param name invalid");
            throw new JokenpoException(MessageException.INVALID_PARAM);
        }
        try {
            this.moveService.deleteByPlayerName(name);
        } catch (JokenpoException ex){
            LOGGER.debug("Player without movement already");
        }
        LOGGER.debug("Finding player by name : {}", name);
        final var entity = this.playerRepository.findByName(name);
        LOGGER.debug("Removing player");
        if(this.playerRepository.delete(entity)){
            return this.getAll();
        }
        LOGGER.error("Error deleting player");
        throw new JokenpoException(MessageException.PLAYER_DELETE_ERROR);
    }

    public void clearAll(){
        PlayerUtils.clear();
    }

    private Boolean verifyIfAlreadyExistsByName(final String name) {
        try {
            if (!Objects.isNull(this.playerRepository.findByName(name))) {
                return true;
            }
        } catch (JokenpoException e) {
            return false;
        }
        return false;
    }

}
