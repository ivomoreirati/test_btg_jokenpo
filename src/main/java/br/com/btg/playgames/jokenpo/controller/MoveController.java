package br.com.btg.playgames.jokenpo.controller;

import br.com.btg.playgames.jokenpo.parameters.MoveRequestDTO;
import br.com.btg.playgames.jokenpo.presenters.base.BaseDTO;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;
import br.com.btg.playgames.jokenpo.service.impl.MoveServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/move")
@CrossOrigin(origins = "*")
public class MoveController {

    private final MoveServiceImpl moveService;

    @Autowired
    public MoveController(final MoveServiceImpl moveService) {
        this.moveService = moveService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> insert(@Valid @RequestBody final MoveRequestDTO moveRequestDTO)
            throws JokenpoException {
        return ResponseEntity.ok(
                new BaseDTO<>(this.moveService.insert(moveRequestDTO)));
    }

    @DeleteMapping(value = "")
    public ResponseEntity<Object> deleteByPlayerName(@PathParam("playerName") final String playerName) throws JokenpoException {
        return ResponseEntity.ok(new BaseDTO<>(this.moveService.deleteByPlayerName(playerName)));
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() throws JokenpoException {
        return ResponseEntity.ok(new BaseDTO<>(this.moveService.getAll()));
    }

}
