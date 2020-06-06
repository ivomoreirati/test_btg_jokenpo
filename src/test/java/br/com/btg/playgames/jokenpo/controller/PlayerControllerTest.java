package br.com.btg.playgames.jokenpo.controller;

import br.com.btg.playgames.jokenpo.parameters.PlayerRequestDTO;
import br.com.btg.playgames.jokenpo.presenters.base.BaseDTO;
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
public class PlayerControllerTest {

    @LocalServerPort
    private int randomServerPort;

    private RestTemplate restTemplate;
    private PlayerServiceImpl playerService;

    @Before
    public void setup(){
        restTemplate = new RestTemplate();
        PlayerRepository playerRepository = new PlayerRepository();
        MoveRepository moveRepository = new MoveRepository();
        MoveServiceImpl moveService = new MoveServiceImpl(moveRepository, playerRepository);
        playerService = new PlayerServiceImpl(playerRepository, moveService);
    }

    @Test
    public void getAllWithoutAnyPlayersInsertedAPI() throws URISyntaxException {
        // Adjust players
        this.playerService.clearAll();
        // Get all players
        ResponseEntity<String> result = restTemplate.getForEntity(getPlayerUri(), String.class);
        // Verify request
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertTrue(Objects.requireNonNull(result.getBody()).contains("data"));
        // Convert to ApiResponse
        final var baseDTO = new Gson().fromJson(result.getBody(), BaseDTO.class);
        // Assertments check
        Assert.assertNotNull(baseDTO.getMetadata().getTimestamp());
        // Convert to playerResponse list
        final var listResponse = new ModelMapper().map(baseDTO.getData(), List.class);
        // Assertments check
        Assert.assertEquals(0, listResponse.size());
    }

    @Test
    public void insertPlayerAPI() throws URISyntaxException {
        // Adjust players
        this.playerService.clearAll();
        // Inser request
        HttpEntity<PlayerRequestDTO> requestForInsert = new HttpEntity<>(
                new PlayerRequestDTO("P1"), new HttpHeaders());
        // Post for insert
        ResponseEntity<String> result = restTemplate.postForEntity(getPlayerUri(), requestForInsert, String.class);
        // Assertments check
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertTrue(Objects.requireNonNull(result.getBody()).contains("data"));
        Assert.assertTrue(result.getBody().contains("playerName"));
        Assert.assertTrue(result.getBody().contains("P1"));
        // Assertment check
        Assert.assertEquals(1, this.getAllPlayers().size());
    }

    @Test
    public void deletePlayerByNameAPI() throws URISyntaxException, JokenpoException {
        // Adjust players
        this.playerService.clearAll();
        this.playerService.insert(new PlayerRequestDTO("P1"));
        // Delete one player
        restTemplate.delete(getPlayerUri() + "/?playerName=P1");
        // Assertment check
        Assert.assertEquals(0, this.getAllPlayers().size());
    }

    private List getAllPlayers() throws URISyntaxException {
        // Get all players
        ResponseEntity<String> result = restTemplate.getForEntity(getPlayerUri(), String.class);
        final var baseDTO = new Gson().fromJson(result.getBody(), BaseDTO.class);
        // Convert to playerResponse list
        return new ModelMapper().map(baseDTO.getData(), List.class);
    }

    private URI getPlayerUri() throws URISyntaxException {
        final var HOST = "http://localhost";
        final var ENDPOINT = "/v1/btg/jokenpo/player";
        final String baseUrl = HOST + ":" + randomServerPort + ENDPOINT;
        return new URI(baseUrl);
    }

}