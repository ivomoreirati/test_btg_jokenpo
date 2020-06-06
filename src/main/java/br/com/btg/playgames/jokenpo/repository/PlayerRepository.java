package br.com.btg.playgames.jokenpo.repository;

import br.com.btg.playgames.jokenpo.model.Player;
import br.com.btg.playgames.jokenpo.constants.MessageException;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;
import br.com.btg.playgames.jokenpo.util.PlayerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


@Repository
@NoRepositoryBean
public class PlayerRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerRepository.class);

    public Player save(Player entity) throws JokenpoException {
        if(PlayerUtils.getInstance() != null) {
            PlayerUtils.getInstance().add(entity);
            return entity;
        }
        LOGGER.error("Error saving");
        throw new JokenpoException(MessageException.PLAYER_SAVE_ERROR);
    }

    public boolean delete(Player entity) throws JokenpoException {
        if(PlayerUtils.getInstance() == null) {
            LOGGER.error("Error deleting");
            throw new JokenpoException(MessageException.PLAYER_DELETE_ERROR);
        }
        return PlayerUtils.getInstance().remove(entity);
    }

    public List<Player> findAll() throws JokenpoException {
        if(PlayerUtils.getInstance() == null) {
            LOGGER.error("Error finding all players");
            throw new JokenpoException(MessageException.PLAYER_FIND_ALL_ERROR);
        }
        return PlayerUtils.getInstance();
    }

    public Player findByName(String name) throws JokenpoException {
        final var list = findAll().stream()
                .filter(elem -> (elem.getName().compareToIgnoreCase(name) == 0))
                .collect(Collectors.toList());
        final var opt = list.stream().findFirst();
        if(opt.isPresent()){
            return opt.get();
        }
        LOGGER.info("Player not found : {}", name);
        throw new JokenpoException(MessageException.PLAYER_NOT_FOUND);
    }

}
