package br.com.btg.playgames.jokenpo.service.impl;

import br.com.btg.playgames.jokenpo.parameters.MoveRequestDTO;
import br.com.btg.playgames.jokenpo.presenters.MoveResponseDTO;
import br.com.btg.playgames.jokenpo.model.Move;
import br.com.btg.playgames.jokenpo.model.Player;
import br.com.btg.playgames.jokenpo.util.mappers.MoveMapper;
import br.com.btg.playgames.jokenpo.constants.MessageException;
import br.com.btg.playgames.jokenpo.constants.MovementConstants;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;
import br.com.btg.playgames.jokenpo.repository.MoveRepository;
import br.com.btg.playgames.jokenpo.repository.PlayerRepository;
import br.com.btg.playgames.jokenpo.service.MoveService;
import br.com.btg.playgames.jokenpo.util.MoveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MoveServiceImpl implements MoveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveServiceImpl.class);

    private final MoveRepository moveRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public MoveServiceImpl(final MoveRepository moveRepository, final PlayerRepository playerRepository){
        this.moveRepository = moveRepository;
        this.playerRepository = playerRepository;
    }

    public MoveResponseDTO insert(final MoveRequestDTO move) throws JokenpoException {
        if(Objects.isNull(move)
                || StringUtils.isEmpty(move.getPlayerName())
                || StringUtils.isEmpty(move.getMovement())){
            LOGGER.error("Invalid movement");
            throw new JokenpoException(MessageException.MOVEMENT_INVALID);
        }
        LOGGER.debug("Move : {}", move.toString());

        // identify the player
        final var player = this.playerRepository.findByName(move.getPlayerName());

        // check if exists just one movement for these player
        this.verifyIfAlreadyMoved(player);

        // identify the movement
        final var movement = MovementConstants.getEnumMovementByName(move.getMovement());

        // save entity object
        final var moveEntity = this.moveRepository.save(new Move(player, movement));

        // convert entity to response
        return MoveMapper.entityToResponse(moveEntity);
    }

    public List<MoveResponseDTO> getAll() throws JokenpoException {
        LOGGER.debug("Finding all movements");
        final var entityList = this.moveRepository.findAll();
        List<MoveResponseDTO> response = new ArrayList<>();
        entityList.forEach(elem -> response.add(MoveMapper.entityToResponse(elem)));
        LOGGER.debug("Movements searched");
        return response;
    }

    public List<MoveResponseDTO> deleteByPlayerName(final String playerName) throws JokenpoException {
        if(StringUtils.isEmpty(playerName)){
            LOGGER.error("Player name invalid");
            throw new JokenpoException(MessageException.INVALID_PARAM);
        }
        LOGGER.debug("Finding movement by player name : {}", playerName);
        final var entity = this.moveRepository.findByPlayerName(playerName);
        LOGGER.debug("Deleting movement");
        if(this.moveRepository.delete(entity)){
            return this.getAll();
        }
        LOGGER.error("Error deleting movement");
        throw new JokenpoException(MessageException.MOVEMENT_DELETE_ERROR);
    }

    public void clearAll(){
        MoveUtils.clear();
    }

    private void verifyIfAlreadyMoved(final Player player) throws JokenpoException {
        final var count = this.moveRepository.findAll()
                .stream()
                .filter(elem ->
                        (elem.getPlayer().getName().compareToIgnoreCase(player.getName()) == 0))
                .count();
        if(count > 0){
            LOGGER.error("Movement already exists for these player");
            throw new JokenpoException(MessageException.MOVEMENT_ALREADY_EXISTS);
        }
    }

}
