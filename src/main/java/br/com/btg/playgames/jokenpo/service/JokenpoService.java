package br.com.btg.playgames.jokenpo.service;

import br.com.btg.playgames.jokenpo.presenters.JokenpoResponseDTO;
import br.com.btg.playgames.jokenpo.presenters.PlayerResponseDTO;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;

import java.util.List;

public interface JokenpoService {

    List<PlayerResponseDTO> clear() throws JokenpoException;

    JokenpoResponseDTO play() throws JokenpoException;

}
