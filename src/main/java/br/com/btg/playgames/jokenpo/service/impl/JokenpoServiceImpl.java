package br.com.btg.playgames.jokenpo.service.impl;

import br.com.btg.playgames.jokenpo.presenters.JokenpoResponseDTO;
import br.com.btg.playgames.jokenpo.presenters.MoveResponseDTO;
import br.com.btg.playgames.jokenpo.presenters.PlayerResponseDTO;
import br.com.btg.playgames.jokenpo.constants.MessageException;
import br.com.btg.playgames.jokenpo.constants.MovementConstants;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;
import br.com.btg.playgames.jokenpo.service.JokenpoService;
import br.com.btg.playgames.jokenpo.util.MoveUtils;
import br.com.btg.playgames.jokenpo.util.PlayerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JokenpoServiceImpl implements JokenpoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JokenpoServiceImpl.class);

    private static final String ZERO_WINS = "NOBODY WON!";
    private static final String ONE_WINS = " IS THE WINNER!";
    private static final String MANY_WINS = "THE WINNERS : ";
    private static final String MANY_WINS_SEPARATOR = " / ";

    private final PlayerServiceImpl playerService;
    private final MoveServiceImpl moveService;

    @Autowired
    public JokenpoServiceImpl(PlayerServiceImpl playerService,
                              MoveServiceImpl moveService){
        this.playerService = playerService;
        this.moveService = moveService;
    }

    public List<PlayerResponseDTO> clear() throws JokenpoException {
        LOGGER.debug("Erasing all data");
        MoveUtils.clear();
        PlayerUtils.clear();
        LOGGER.debug("Data erased");
        return this.playerService.getAll();
    }

    public JokenpoResponseDTO play() throws JokenpoException {
        this.checkRequirements();
        List<String> winners = new ArrayList<>();
        LOGGER.debug("Generating result");
        this.moveService.getAll()
                .forEach(obj -> {
                    try {
                        if(checkIfIsTheWinner(obj.getMovement().getWeakness())){
                            winners.add(obj.getPlayer().getName());
                        }
                    } catch (JokenpoException e) {
                        LOGGER.error("Error detecting winners - Player Name : {} - Error Message : {}",
                                obj.getPlayer().getName(), e.getMessage());
                    }
                });
        LOGGER.debug("Result generated");

        JokenpoResponseDTO gameResult = new JokenpoResponseDTO(this.getWinnersMessage(winners),
                this.getHistoryFromMovements(this.moveService.getAll()));
        LOGGER.debug("Winners message formatted");

        LOGGER.debug("Erasing movements data");
        MoveUtils.clear();

        LOGGER.debug("Round finished");
        return gameResult;
    }

    private void checkRequirements() throws JokenpoException {
        if(this.playerService.getAll().size() == 0){
            throw new JokenpoException(MessageException.NOBODY_PLAYING);
        } else if (this.playerService.getAll().size() <= 1){
            throw new JokenpoException(MessageException.INSUFFICIENT_PLAYERS);
        } else if (this.moveService.getAll().size() <= 1){
            throw new JokenpoException(MessageException.INSUFFICIENT_MOVEMENTS);
        } else if (this.moveService.getAll().size() != this.playerService.getAll().size()){
            throw new JokenpoException(MessageException.PLAYERS_PENDING);
        }
    }

    private Boolean checkIfIsTheWinner(final List<MovementConstants> weakness) throws JokenpoException {
        for (MovementConstants movementConstants : weakness) {
            LOGGER.debug("Checking weakness : {}", movementConstants.getName());
            for(MoveResponseDTO resp : this.moveService.getAll()){
                if(resp.getMovement().getName().compareTo(movementConstants.getName()) == 0){
                    LOGGER.debug("LOSER - Lost to {} - {}", resp.getPlayer().getName(), movementConstants.getName());
                    return false;
                }
            }
        }
        LOGGER.debug("WINNER DETECTED");
        return true;
    }

    private String getWinnersMessage(final List<String> winners){
        StringBuilder message = new StringBuilder();
        if(winners.size() == 0){
            message = new StringBuilder(ZERO_WINS);
        } else if(winners.size() == 1) {
            message = new StringBuilder(winners.get(0).toUpperCase().trim() + ONE_WINS);
        } else {
            message = new StringBuilder(MANY_WINS);
            int counter = 0;
            for(String name : winners){
                counter++;
                if(counter == winners.size()){
                    message.append(name);
                } else {
                    message.append(name).append(MANY_WINS_SEPARATOR);
                }
            }
        }
        return message.toString();
    }

    private List<String> getHistoryFromMovements(final List<MoveResponseDTO> list) {
        List<String> result = new ArrayList<>();
        for(MoveResponseDTO resp : list){
            String message = resp.getPlayer().getName() + " (" + resp.getMovement().getName() + ")";
            result.add(message);
        }
        return result;
    }

}
