package br.com.btg.playgames.jokenpo.controller;

import br.com.btg.playgames.jokenpo.parameters.PlayerRequestDTO;
import br.com.btg.playgames.jokenpo.presenters.base.BaseDTO;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;
import br.com.btg.playgames.jokenpo.service.impl.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/player")
@CrossOrigin(origins = "*")
public class PlayerController {

    private final PlayerServiceImpl playerService;

    @Autowired
    public PlayerController(final PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> insert(@Valid @RequestBody PlayerRequestDTO playerRequestDTO)
            throws JokenpoException {
        return ResponseEntity.ok(
                new BaseDTO<>(this.playerService.insert(playerRequestDTO)));
    }

    @DeleteMapping(value = "")
    public ResponseEntity<Object> deleteByPlayerName(@PathParam("playerName") final String playerName) throws JokenpoException {
        return ResponseEntity.ok(new BaseDTO<>(this.playerService.deleteByName(playerName)));
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() throws JokenpoException {
        return ResponseEntity.ok(new BaseDTO<>(this.playerService.getAll()));
    }

}
