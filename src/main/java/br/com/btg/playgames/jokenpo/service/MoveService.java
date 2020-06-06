package br.com.btg.playgames.jokenpo.service;

import br.com.btg.playgames.jokenpo.parameters.MoveRequestDTO;
import br.com.btg.playgames.jokenpo.presenters.MoveResponseDTO;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;

import java.util.List;

public interface MoveService {

    MoveResponseDTO insert(MoveRequestDTO move) throws JokenpoException;

    List<MoveResponseDTO> getAll() throws JokenpoException;

    List<MoveResponseDTO> deleteByPlayerName(String playerName) throws JokenpoException;

    void clearAll();

}
