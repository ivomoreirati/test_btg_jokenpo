package br.com.btg.playgames.jokenpo.repository;

import br.com.btg.playgames.jokenpo.constants.MessageException;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;
import br.com.btg.playgames.jokenpo.model.Move;
import br.com.btg.playgames.jokenpo.util.MoveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@NoRepositoryBean
public class MoveRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveRepository.class);

    public Move save(final Move entity) throws JokenpoException {
        if(MoveUtils.getInstance() != null) {
            MoveUtils.getInstance().add(entity);
            return entity;
        }
        LOGGER.error("Error saving");
        throw new JokenpoException(MessageException.MOVEMENT_SAVE_ERROR);
    }

    public boolean delete(final Move entity) throws JokenpoException {
        if(MoveUtils.getInstance() == null) {
            LOGGER.error("Error deleting");
            throw new JokenpoException(MessageException.MOVEMENT_DELETE_ERROR);
        }
        return MoveUtils.getInstance().remove(entity);
    }

    public List<Move> findAll() throws JokenpoException {
        if(MoveUtils.getInstance() == null) {
            LOGGER.error("Error finding all movements");
            throw new JokenpoException(MessageException.MOVEMENT_FIND_ALL_ERROR);
        }
        return MoveUtils.getInstance();
    }

    public Move findByPlayerName(final String playerName) throws JokenpoException {
        final var list = findAll().stream()
                .filter(elem -> (elem.getPlayer().getName().compareToIgnoreCase(playerName) == 0))
                .collect(Collectors.toList());
        final var opt = list.stream().findFirst();
        if(opt.isPresent()){
            return opt.get();
        }
        LOGGER.error("Player movement not found : {}", playerName);
        throw new JokenpoException(MessageException.MOVEMENT_NOT_FOUND);
    }

}
