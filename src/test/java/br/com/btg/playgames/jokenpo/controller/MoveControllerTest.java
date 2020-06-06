package br.com.btg.playgames.jokenpo.controller;

import br.com.btg.playgames.jokenpo.parameters.MoveRequestDTO;
import br.com.btg.playgames.jokenpo.parameters.PlayerRequestDTO;
import br.com.btg.playgames.jokenpo.presenters.base.BaseDTO;
import br.com.btg.playgames.jokenpo.constants.MovementConstants;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;
import br.com.btg.playgames.jokenpo.repository.MoveRepository;
import br.com.btg.playgames.jokenpo.repository.PlayerRepository;
import br.com.btg.playgames.jokenpo.service.impl.MoveServiceImpl;
import br.com.btg.playgames.jokenpo.service.impl.PlayerServiceImpl;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MoveControllerTest {

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
    public void getAllWithoutAnyMovementInsertedAPI() throws URISyntaxException {
        // Adjust players and movements
        this.playerService.clearAll();
        this.moveService.clearAll();
        // Get all movements
        final var result = restTemplate.getForEntity(getMovementsUri(), String.class);
        // Verify request
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertTrue(Objects.requireNonNull(result.getBody()).contains("data"));
        Assert.assertFalse(result.getBody().contains("player"));
        Assert.assertFalse(result.getBody().contains("movement"));
        // Convert
        var baseDTO = new Gson().fromJson(result.getBody(), BaseDTO.class);
        var listResponse = new ModelMapper().map(baseDTO.getData(), List.class);
        // Assertments check
        Assert.assertEquals(0, listResponse.size());
    }

    @Test
    public void insertMovementAPI() throws URISyntaxException, JokenpoException {
        // Adjust player
        this.playerService.clearAll();
        this.playerService.insert(new PlayerRequestDTO("P2"));
        // Request object
        final var moveRequestDTO = new MoveRequestDTO("P2", MovementConstants.PAPER.getName());
        final var requestForInsert = new HttpEntity<>(moveRequestDTO, new HttpHeaders());
        // Post for insert
        final var result = restTemplate.postForEntity(getMovementsUri(), requestForInsert, String.class);
        // Assertments check
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertTrue(Objects.requireNonNull(result.getBody()).contains("data"));
        Assert.assertTrue(result.getBody().contains("movement"));
        // Assertment check
        Assert.assertNotEquals(0, this.getAllMovements().size());
    }

    @Test
    public void deleteMovementByNameAPI() throws URISyntaxException, JokenpoException {
        // Adjust player and movement
        this.playerService.clearAll();
        this.moveService.clearAll();
        this.playerService.insert(new PlayerRequestDTO("P3"));
        this.moveService.insert(new MoveRequestDTO("P3", MovementConstants.SPOCK.getName()));
        // Delete one movement
        restTemplate.delete(getMovementsUri() + "/?playerName=P3");
        // Assertment check
        Assert.assertEquals(0, this.getAllMovements().size());
    }

    private List getAllMovements() throws URISyntaxException {
        // Get all movements
        ResponseEntity<String> result = restTemplate.getForEntity(getMovementsUri(), String.class);
        var baseDTO = new Gson().fromJson(result.getBody(), BaseDTO.class);
        // Convert to moveResponse list
        return new ModelMapper().map(baseDTO.getData(), List.class);
    }

    private URI getMovementsUri() throws URISyntaxException {
        final var HOST = "http://localhost";
        final var ENDPOINT = "/v1/btg/jokenpo/move";
        final String baseUrl = HOST + ":" + randomServerPort + ENDPOINT;
        return new URI(baseUrl);
    }

}