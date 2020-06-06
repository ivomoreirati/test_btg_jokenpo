package br.com.btg.playgames.jokenpo.service;

import br.com.btg.playgames.jokenpo.parameters.MoveRequestDTO;
import br.com.btg.playgames.jokenpo.presenters.MoveResponseDTO;
import br.com.btg.playgames.jokenpo.parameters.PlayerRequestDTO;
import br.com.btg.playgames.jokenpo.presenters.PlayerResponseDTO;
import br.com.btg.playgames.jokenpo.constants.MovementConstants;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;
import br.com.btg.playgames.jokenpo.service.impl.MoveServiceImpl;
import br.com.btg.playgames.jokenpo.service.impl.PlayerServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MoveServiceTest {

    @Autowired
    private PlayerServiceImpl playerService;

    @Autowired
    private MoveServiceImpl moveService;

    @Test
    public void insertManyPlayersForTestWithSucess() throws JokenpoException {
        // Clear singleton data
        this.playerService.clearAll();
        this.moveService.clearAll();
        // Insert many players
        List<String> playerNames = new ArrayList<>(Arrays.asList("P1", "P2", "P3", "P4", "P5", "P6"));
        List<PlayerResponseDTO> playerResponseDTO = this.insertManyDifferentPlayers(playerNames);
        // Assertments check
        Assert.assertEquals(playerNames.size(), playerResponseDTO.size());
    }

    @Test
    public void playersWithoutMovements() throws JokenpoException {
        this.insertManyPlayersForTestWithSucess();
        int playersCounter = this.playerService.getAll().size();
        int movementsCounter = this.moveService.getAll().size();
        // Assertments check
        Assert.assertEquals(0, movementsCounter);
        Assert.assertNotEquals(0, playersCounter);
    }

    @Test
    public void insertOneMovement() throws JokenpoException {
        this.insertManyPlayersForTestWithSucess();
        int expected = 1;
        MoveResponseDTO response = this.moveService.insert(new MoveRequestDTO("P1", "STONE"));
        // Assertments check
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getMovement());
        Assert.assertNotNull(response.getPlayer());
        Assert.assertEquals(expected, this.moveService.getAll().size());
    }

    @Test(expected = JokenpoException.class)
    public void insertDuplicatedMovementForSamePlayerWithException() throws JokenpoException {
        this.insertManyPlayersForTestWithSucess();
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.STONE.getName()));
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.LIZARD.getName()));
    }

    @Test(expected = JokenpoException.class)
    public void insertDuplicatedMovementForSamePlayerAndSameMovementWithException() throws JokenpoException {
        this.insertManyPlayersForTestWithSucess();
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.STONE.getName()));
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.STONE.getName()));
    }

    @Test
    public void insertMovementForDifferentPlayersWithSucess() throws JokenpoException {
        this.insertManyPlayersForTestWithSucess();
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.STONE.getName()));
        this.moveService.insert(new MoveRequestDTO("P2", MovementConstants.LIZARD.getName()));
        this.moveService.insert(new MoveRequestDTO("P3", MovementConstants.STONE.getName()));
        this.moveService.insert(new MoveRequestDTO("P4", MovementConstants.SCISSORS.getName()));
        this.moveService.insert(new MoveRequestDTO("P5", MovementConstants.PAPER.getName()));
        // Assertments check
        Assert.assertEquals(5, this.moveService.getAll().size());
    }

    @Test
    public void deleteOneMovementWithSucess() throws JokenpoException {
        this.insertManyPlayersForTestWithSucess();
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.SPOCK.getName()));
        this.moveService.insert(new MoveRequestDTO("P2", MovementConstants.STONE.getName()));
        this.moveService.insert(new MoveRequestDTO("P3", MovementConstants.SCISSORS.getName()));
        int beforeCounter = this.moveService.getAll().size();
        this.moveService.deleteByPlayerName("P2");
        // Assertments check
        Assert.assertEquals(beforeCounter-1, this.moveService.getAll().size());
    }

    @Test
    public void deleteOneMovementAfterInsertAnotherWithSucess() throws JokenpoException {
        this.insertManyPlayersForTestWithSucess();
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.SPOCK.getName()));
        this.moveService.insert(new MoveRequestDTO("P2", MovementConstants.STONE.getName()));
        this.moveService.insert(new MoveRequestDTO("P3", MovementConstants.SCISSORS.getName()));
        this.moveService.deleteByPlayerName("P2");
        this.moveService.insert(new MoveRequestDTO("P2", MovementConstants.PAPER.getName()));
        // Assertments check
        Assert.assertEquals(3, this.moveService.getAll().size());
    }

    @Test
    public void clearAllMovementsWithSucess() throws JokenpoException {
        this.insertManyPlayersForTestWithSucess();
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.SPOCK.getName()));
        this.moveService.insert(new MoveRequestDTO("P2", MovementConstants.STONE.getName()));
        this.moveService.insert(new MoveRequestDTO("P3", MovementConstants.SCISSORS.getName()));
        Assert.assertNotEquals(0, this.moveService.getAll().size());
        this.moveService.clearAll();
        Assert.assertEquals(0, this.moveService.getAll().size());
    }

    private List<PlayerResponseDTO> insertManyDifferentPlayers(List<String> playerNames) throws JokenpoException {
        List<PlayerResponseDTO> list = new ArrayList<>();
        for(String name : playerNames){
            PlayerResponseDTO playerResponseDTO = this.playerService.insert(new PlayerRequestDTO(name));
            list.add(playerResponseDTO);
        }
        return list;
    }

}
