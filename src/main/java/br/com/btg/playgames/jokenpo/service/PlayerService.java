package br.com.btg.playgames.jokenpo.service;

import br.com.btg.playgames.jokenpo.parameters.PlayerRequestDTO;
import br.com.btg.playgames.jokenpo.presenters.PlayerResponseDTO;
import br.com.btg.playgames.jokenpo.model.Player;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;

import java.util.List;

public interface PlayerService {

    PlayerResponseDTO insert(PlayerRequestDTO player) throws JokenpoException;

    List<PlayerResponseDTO> getAll() throws JokenpoException;

    Player getEntityByName(String name) throws JokenpoException;

    List<PlayerResponseDTO> deleteByName(String name) throws JokenpoException;

    void clearAll();

}
