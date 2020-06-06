package br.com.btg.playgames.jokenpo.service;

import br.com.btg.playgames.jokenpo.parameters.PlayerRequestDTO;
import br.com.btg.playgames.jokenpo.presenters.PlayerResponseDTO;
import br.com.btg.playgames.jokenpo.model.Player;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;
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
public class PlayerServiceTest {

    @Autowired
    private PlayerServiceImpl playerService;

    @Test
    public void oneInsertWithSucess() throws Exception {
        // Clear singleton data
        this.playerService.clearAll();
        // Insert just one player
        final var expectedPlayerName = "PLAYER NAME";
        PlayerResponseDTO playerResponseDTO = this.playerService.insert(new PlayerRequestDTO(expectedPlayerName));
        Assert.assertEquals(expectedPlayerName, playerResponseDTO.getName());
    }

    @Test(expected = JokenpoException.class)
    public void duplicateInsertForExceptionGenerate() throws Exception {
        // Clear singleton data
        this.playerService.clearAll();
        // Insert two twices the same player name
        final var samePlayerName = "DUPLICATED NAME";
        this.playerService.insert(new PlayerRequestDTO(samePlayerName));
        this.playerService.insert(new PlayerRequestDTO(samePlayerName));
    }

    @Test
    public void manyDifferentInsertsWithSuccess() throws Exception {
        // Clear singleton data
        this.playerService.clearAll();
        // Insert many players
        final var playerNames = new ArrayList<>(Arrays.asList("P1", "P2", "P3", "P4", "P5"));
        final var playerResponsDTOS = this.insertManyDifferentPlayers(playerNames);
        // Assertments check
        int position = 0;
        for (String expectedName : playerNames) {
            Assert.assertEquals(expectedName, playerResponsDTOS.get(position).getName());
            position++;
        }
    }

    @Test
    public void getAllPlayersWithSucess() throws Exception {
        // Clear singleton data
        this.playerService.clearAll();
        // Insert many players
        final var playerNames = new ArrayList<>(Arrays.asList("P1", "P2", "P3", "P4", "P5"));
        final var playerResponsDTOS = this.insertManyDifferentPlayers(playerNames);
        // Assertments check
        Assert.assertEquals(playerNames.size(), playerResponsDTOS.size());
        Assert.assertEquals(playerNames.size(), this.playerService.getAll().size());
        Assert.assertEquals(playerResponsDTOS.size(), this.playerService.getAll().size());
    }

    @Test
    public void getEntityByNameWithSucess() throws Exception {
        // Clear singleton data
        this.playerService.clearAll();
        // Insert many players
        final var playerNames = new ArrayList<>(Arrays.asList("P1", "P2", "P3", "P4", "P5"));
        this.insertManyDifferentPlayers(playerNames);
        Player entity = this.playerService.getEntityByName("P2");
        // Assertments check
        Assert.assertEquals("P2", entity.getName());
    }

    @Test(expected = JokenpoException.class)
    public void getEntityByNameWithException() throws Exception {
        // Clear singleton data
        this.playerService.clearAll();
        // Insert many players
        final var playerNames = new ArrayList<>(Arrays.asList("P1", "P2", "P3", "P4", "P5"));
        this.insertManyDifferentPlayers(playerNames);
        this.playerService.getEntityByName("INEXISTENTE");
    }

    @Test
    public void deleteByNameWithSucess() throws Exception {
        // Clear singleton data
        this.playerService.clearAll();
        // Insert many players
        final var playerNames = new ArrayList<>(Arrays.asList("P1", "P2", "P3", "P4", "P5"));
        final var playerResponseDTO = this.insertManyDifferentPlayers(playerNames);
        int expected1 = playerNames.size()-1;
        int expected2 = playerResponseDTO.size()-1;
        final var list = this.playerService.deleteByName("P1");
        // Assertments check
        Assert.assertEquals(expected1, list.size());
        Assert.assertEquals(expected2, list.size());
    }

    @Test(expected = JokenpoException.class)
    public void deleteByNameWithException() throws Exception {
        // Clear singleton data
        this.playerService.clearAll();
        // Insert many players
        final var playerNames = new ArrayList<>(Arrays.asList("P1", "P2", "P3", "P4", "P5"));
        this.insertManyDifferentPlayers(playerNames);
        this.playerService.deleteByName("INEXISTENTE");
    }

    @Test
    public void clearAllWithSucess() throws Exception {
        // Clear singleton data
        this.playerService.clearAll();
        // Insert many players
        final var playerNames = new ArrayList<>(Arrays.asList("P1", "P2", "P3", "P4", "P5"));
        var playerResponseDTO = this.insertManyDifferentPlayers(playerNames);
        Assert.assertEquals(playerNames.size(), playerResponseDTO.size());
        // Clear singleton data
        this.playerService.clearAll();
        // List update
        playerResponseDTO = this.playerService.getAll();
        // Assertments check
        Assert.assertEquals(0, playerResponseDTO.size());
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
