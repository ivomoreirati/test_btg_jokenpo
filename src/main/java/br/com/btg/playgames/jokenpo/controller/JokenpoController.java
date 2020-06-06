package br.com.btg.playgames.jokenpo.controller;

import br.com.btg.playgames.jokenpo.exception.JokenpoException;
import br.com.btg.playgames.jokenpo.presenters.base.BaseDTO;
import br.com.btg.playgames.jokenpo.service.impl.JokenpoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/play")
@CrossOrigin(origins = "*")
public class JokenpoController {

    private final JokenpoServiceImpl jokenpoService;

    @Autowired
    public JokenpoController(final JokenpoServiceImpl jokenpoService) {
        this.jokenpoService = jokenpoService;
    }

    @DeleteMapping(value = "")
    public ResponseEntity<Object> clear() throws JokenpoException {
        return ResponseEntity.ok(new BaseDTO<>(this.jokenpoService.clear()));
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> play() throws JokenpoException {
        return ResponseEntity.ok(new BaseDTO<>(this.jokenpoService.play()));
    }

}
