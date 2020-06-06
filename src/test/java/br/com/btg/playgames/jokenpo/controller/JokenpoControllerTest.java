package br.com.btg.playgames.jokenpo.controller;

import br.com.btg.playgames.jokenpo.parameters.MoveRequestDTO;
import br.com.btg.playgames.jokenpo.parameters.PlayerRequestDTO;
import br.com.btg.playgames.jokenpo.constants.MovementConstants;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;
import br.com.btg.playgames.jokenpo.repository.MoveRepository;
import br.com.btg.playgames.jokenpo.repository.PlayerRepository;
import br.com.btg.playgames.jokenpo.service.impl.MoveServiceImpl;
import br.com.btg.playgames.jokenpo.service.impl.PlayerServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class JokenpoControllerTest {

    @LocalServerPort
    private int randomServerPort;

    private RestTemplate restTemplate;
    private MoveServiceImpl moveService;
    private PlayerServiceImpl playerService;

    @Before
    public void setup(){
        restTemplate = new RestTemplate();
        PlayerRepository playerRepository = new PlayerRepository();
        MoveRepository moveRepository = new MoveRepository();
        moveService = new MoveServiceImpl(moveRepository, playerRepository);
        playerService = new PlayerServiceImpl(playerRepository, moveService);
    }

    @Test
    public void playWithNobodyWonAPI() throws URISyntaxException, JokenpoException {
        // Insert players and movements
        this.playerService.clearAll();
        this.moveService.clearAll();
        this.playerService.insert(new PlayerRequestDTO("P1"));
        this.playerService.insert(new PlayerRequestDTO("P2"));
        this.playerService.insert(new PlayerRequestDTO("P3"));
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.SPOCK.getName()));
        this.moveService.insert(new MoveRequestDTO("P2", MovementConstants.SCISSORS.getName()));
        this.moveService.insert(new MoveRequestDTO("P3", MovementConstants.PAPER.getName()));
        // Play
        ResponseEntity<String> result = restTemplate.getForEntity(getJokenpoUri(), String.class);
        // Verify request
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertTrue(Objects.requireNonNull(result.getBody()).contains("data"));
        Assert.assertTrue(result.getBody().contains("history"));
        Assert.assertTrue(result.getBody().contains("result"));
        Assert.assertTrue(result.getBody().contains("NOBODY WON"));
    }

    @Test
    public void playWithWinnerAPI() throws URISyntaxException, JokenpoException {
        // Insert players and movements
        this.playerService.clearAll();
        this.moveService.clearAll();
        this.playerService.insert(new PlayerRequestDTO("P1"));
        this.playerService.insert(new PlayerRequestDTO("P2"));
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.SCISSORS.getName()));
        this.moveService.insert(new MoveRequestDTO("P2", MovementConstants.PAPER.getName()));
        // Play
        ResponseEntity<String> result = restTemplate.getForEntity(getJokenpoUri(), String.class);
        // Verify request
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertTrue(Objects.requireNonNull(result.getBody()).contains("data"));
        Assert.assertTrue(result.getBody().contains("history"));
        Assert.assertTrue(result.getBody().contains("result"));
        Assert.assertTrue(result.getBody().contains("P1 IS THE WINNER"));
    }

    @Test
    public void clearAllAPI() throws URISyntaxException, JokenpoException {
        // Insert players and movements
        this.playerService.clearAll();
        this.moveService.clearAll();
        this.playerService.insert(new PlayerRequestDTO("P1"));
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.PAPER.getName()));
        // Assertment check
        Assert.assertEquals(1, this.playerService.getAll().size());
        Assert.assertEquals(1, this.moveService.getAll().size());
        // Clear all by API call
        restTemplate.delete(getJokenpoUri() + "/?playerName=P1");
        // Assertment check
        Assert.assertEquals(0, this.playerService.getAll().size());
        Assert.assertEquals(0, this.moveService.getAll().size());
    }

    private URI getJokenpoUri() throws URISyntaxException {
        String HOST = "http://localhost";
        String ENDPOINT = "/v1/btg/jokenpo/play";
        final String baseUrl = HOST + ":" + randomServerPort + ENDPOINT;
        return new URI(baseUrl);
    }

}