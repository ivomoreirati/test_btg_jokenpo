package br.com.btg.playgames.jokenpo.service;

import br.com.btg.playgames.jokenpo.constants.MovementConstants;
import br.com.btg.playgames.jokenpo.exception.JokenpoException;
import br.com.btg.playgames.jokenpo.parameters.MoveRequestDTO;
import br.com.btg.playgames.jokenpo.parameters.PlayerRequestDTO;
import br.com.btg.playgames.jokenpo.presenters.JokenpoResponseDTO;
import br.com.btg.playgames.jokenpo.service.impl.JokenpoServiceImpl;
import br.com.btg.playgames.jokenpo.service.impl.MoveServiceImpl;
import br.com.btg.playgames.jokenpo.service.impl.PlayerServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
public class JokenpoServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private PlayerServiceImpl playerService;

    @Autowired
    private MoveServiceImpl moveService;

    @Autowired
    private JokenpoServiceImpl jokenpoService;

    @Before
    public void setup(){
        this.clearAllData();
    }

    @Test
    public void clearAllDataWithSucess() throws JokenpoException {
        // Players insert
        insertSomePlayers(Arrays.asList("P1", "P2", "P3", "P4", "P5", "P6"));
        // Movements insert
        insertSomeMovements(
            Arrays.asList(
                new MoveRequestDTO("P1", MovementConstants.SPOCK.getName()),
                new MoveRequestDTO("P2", MovementConstants.PAPER.getName()),
                new MoveRequestDTO("P3", MovementConstants.SCISSORS.getName())
            )
        );
        // Assertments check
        Assert.assertNotEquals(0, this.playerService.getAll().size());
        Assert.assertNotEquals(0, this.moveService.getAll().size());
        this.jokenpoService.clear();
        Assert.assertEquals(0, this.playerService.getAll().size());
        Assert.assertEquals(0, this.moveService.getAll().size());
    }

    @Test
    public void paperVersusScissorsPlaying() throws JokenpoException {
        // Players insert
        this.insertSomePlayers(Arrays.asList("P1", "P2"));
        // Movements insert
        insertSomeMovements(
            Arrays.asList(
                new MoveRequestDTO("P1", MovementConstants.PAPER.getName()),
                new MoveRequestDTO("P2", MovementConstants.SCISSORS.getName())
            )
        );
        // Action
        JokenpoResponseDTO response = this.jokenpoService.play();
        // Assertments check
        Assert.assertNotNull(response.getGameResult());
        final var expected = "P2 IS THE WINNER!".toUpperCase().trim();
        Assert.assertEquals(expected, response.getGameResult());
    }

    @Test
    public void paperVersusScissorsVersusStonePlaying() throws JokenpoException {
        // Players insert
        insertSomePlayers(Arrays.asList("P1", "P2", "P3"));
        // Movements insert
        insertSomeMovements(
            Arrays.asList(
                new MoveRequestDTO("P1", MovementConstants.PAPER.getName()),
                new MoveRequestDTO("P2", MovementConstants.SCISSORS.getName()),
                new MoveRequestDTO("P3", MovementConstants.STONE.getName())
            )
        );
        // Action
        final var response = this.jokenpoService.play();
        // Assertments check
        Assert.assertNotNull(response.getGameResult());
        final var expected = "NOBODY WON!".toUpperCase().trim();
        Assert.assertEquals(expected, response.getGameResult());
    }

    @Test
    public void lizardVersusScissorsVersusPaperPlaying() throws JokenpoException {
        // Players insert
        insertSomePlayers(Arrays.asList("P1", "P2", "P3"));
        // Movements insert
        insertSomeMovements(
            Arrays.asList(
                new MoveRequestDTO("P1", MovementConstants.LIZARD.getName()),
                new MoveRequestDTO("P2", MovementConstants.SCISSORS.getName()),
                new MoveRequestDTO("P3", MovementConstants.PAPER.getName())
            )
        );
        // Action
        final var response = this.jokenpoService.play();
        // Assertments check
        Assert.assertNotNull(response.getGameResult());
        final var expected = "P2 IS THE WINNER!".toUpperCase().trim();
        final var notExpected = "NOBODY WON!".toUpperCase().trim();
        Assert.assertNotEquals(notExpected, response.getGameResult());
        Assert.assertEquals(expected, response.getGameResult());
    }

    @Test
    public void spockVersusPaperPlaying() throws JokenpoException {
        // Players insert
        insertSomePlayers(Arrays.asList("P1", "P2"));
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequestDTO("P1", MovementConstants.SPOCK.getName()),
                        new MoveRequestDTO("P2", MovementConstants.PAPER.getName())
                )
        );
        // Action
        final var response = this.jokenpoService.play();
        // Assertments check
        Assert.assertNotNull(response.getGameResult());
        final var expected = "P2 IS THE WINNER!".toUpperCase().trim();
        final var notExpected = "NOBODY WON!".toUpperCase().trim();
        Assert.assertNotEquals(notExpected, response.getGameResult());
        Assert.assertEquals(expected, response.getGameResult());
    }

    @Test
    public void lizardVersusScissorsPlaying() throws JokenpoException {
        // Players insert
        insertSomePlayers(Arrays.asList("P1", "P2"));
        // Movements insert
        insertSomeMovements(
            Arrays.asList(
                new MoveRequestDTO("P1", MovementConstants.SCISSORS.getName()),
                new MoveRequestDTO("P2", MovementConstants.LIZARD.getName())
            )
        );
        // Action
        final var response = this.jokenpoService.play();
        // Assertments check
        Assert.assertNotNull(response.getGameResult());
        final var expected = "P1 IS THE WINNER!".toUpperCase().trim();
        final var notExpected = "NOBODY WON!".toUpperCase().trim();
        Assert.assertNotEquals(notExpected, response.getGameResult());
        Assert.assertEquals(expected, response.getGameResult());
    }

    @Test
    public void invalidMovementPlayingWithExpectException() throws JokenpoException {
        // Players insert
        insertSomePlayers(Arrays.asList("P1", "P2"));

        // Error Expected
        thrown.expect(JokenpoException.class);
        thrown.expectMessage("ERR-2001 - Movement not found");

        // Movements insert
        insertSomeMovements(
            Arrays.asList(
                new MoveRequestDTO("P1", MovementConstants.SCISSORS.getName()),
                new MoveRequestDTO("P2", "OTHER_MOVEMENT")
            )
        );
    }

    @Test
    public void someMovementsPossibilitiesWithSucess() throws JokenpoException {
        // Players insert
        insertSomePlayers(Arrays.asList("P1", "P2", "P3"));
        // First movements
        movementGroupWithDifferentMovements1();
        this.jokenpoService.play();
        // Second movements
        movementGroupWithDifferentMovements2();
        this.jokenpoService.play();
        // Third movements
        movementGroupWithTwoEqualsMovements();
        this.jokenpoService.play();
        // Fourth movements
        movementGroupWithAllEqualsMovements();
        this.jokenpoService.play();
    }

    @Test
    public void playingRemovingAndIncludingSomeMovements() throws JokenpoException {
        // Players insert
        insertSomePlayers(Arrays.asList("P1", "P2"));
        // Movements insert
        this.moveService.insert(new MoveRequestDTO("P2", MovementConstants.PAPER.getName()));
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.SCISSORS.getName()));
        // Action
        var response = this.jokenpoService.play();
        // Assertments check
        Assert.assertEquals("P1 IS THE WINNER!", response.getGameResult());
        // Movements insert
        this.moveService.insert(new MoveRequestDTO("P2", MovementConstants.PAPER.getName()));
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.STONE.getName()));
        // Movement remove
        this.moveService.deleteByPlayerName("P2");
        // Assertment check
        Assert.assertEquals(1, this.moveService.getAll().size());
        // Movement insert
        this.moveService.insert(new MoveRequestDTO("P2", MovementConstants.SPOCK.getName()));
        // Action
        response = this.jokenpoService.play();
        // Assertment check
        Assert.assertEquals("P2 IS THE WINNER!", response.getGameResult());
    }

    @Test
    public void playingRemovingAndIncludingSomePlayers1() throws JokenpoException {
        // Players insert
        insertSomePlayers(Arrays.asList("P1", "P2"));
        // Movements insert
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.SCISSORS.getName()));
        this.moveService.insert(new MoveRequestDTO("P2", MovementConstants.STONE.getName()));
        // Player remove
        this.playerService.deleteByName("P1");
        // Expected exception
        thrown.expect(JokenpoException.class);
        // Action
        this.jokenpoService.play();
    }

    @Test
    public void playingRemovingAndIncludingSomePlayers2() throws JokenpoException {
        // Players insert
        insertSomePlayers(Arrays.asList("P1", "P2"));
        // Movements insert
        this.moveService.insert(new MoveRequestDTO("P1", MovementConstants.SCISSORS.getName()));
        this.moveService.insert(new MoveRequestDTO("P2", MovementConstants.STONE.getName()));
        // Player remove
        this.playerService.deleteByName("P1");
        // Player include
        this.playerService.insert(new PlayerRequestDTO("P7"));
        // Movement insert
        this.moveService.insert(new MoveRequestDTO("P7", MovementConstants.PAPER.getName()));
        // Action
        JokenpoResponseDTO response = this.jokenpoService.play();
        // Assertment check
        Assert.assertNotEquals(0, response.getHistory());
        Assert.assertEquals("P7 IS THE WINNER!", response.getGameResult());
    }

    @Test
    public void historyAfterPlayedWithSucess() throws JokenpoException {
        // Players insert
        insertSomePlayers(Arrays.asList("P1", "P2", "P3"));
        // Movements insert
        insertSomeMovements(
            Arrays.asList(
                new MoveRequestDTO("P1", MovementConstants.SCISSORS.getName()),
                new MoveRequestDTO("P2", MovementConstants.LIZARD.getName()),
                new MoveRequestDTO("P3", MovementConstants.STONE.getName())
            )
        );
        // Action
        JokenpoResponseDTO response = this.jokenpoService.play();
        // Assertments check
        Assert.assertNotEquals(0, response.getHistory().size());
        Assert.assertEquals(3, response.getHistory().size());
    }

    @Test
    public void historyBeforePlayWithExpectException() throws JokenpoException {
        // Players insert
        insertSomePlayers(Arrays.asList("P1", "P2", "P3", "P4", "P5"));
        // Expect Exception
        thrown.expect(JokenpoException.class);
        // Action
        JokenpoResponseDTO response = this.jokenpoService.play();
    }

    private void insertSomePlayers(final List<String> playerNameList) {
        final var list = new ArrayList<>();
        playerNameList
            .forEach(playerName -> {
                try {
                    list.add(this.playerService.insert(new PlayerRequestDTO(playerName)));
                } catch (JokenpoException e){
                    e.printStackTrace();
                }
            }
        );
    }

    private void insertSomeMovements(List<MoveRequestDTO> movementList) throws JokenpoException {
        final var list = new ArrayList<>();
        for(MoveRequestDTO movement : movementList)
            list.add(this.moveService.insert(movement));
    }

    private void clearAllData() {
        this.playerService.clearAll();
        this.moveService.clearAll();
    }

    private void movementGroupWithDifferentMovements1() throws JokenpoException {
        // Movements cleared
        this.moveService.clearAll();
        // Movements insert
        insertSomeMovements(
            Arrays.asList(
                new MoveRequestDTO("P1", MovementConstants.SCISSORS.getName()),
                new MoveRequestDTO("P2", MovementConstants.LIZARD.getName()),
                new MoveRequestDTO("P3", MovementConstants.STONE.getName())
            )
        );
    }

    private void movementGroupWithDifferentMovements2() throws JokenpoException {
        // Movements cleared
        this.moveService.clearAll();
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequestDTO("P1", MovementConstants.STONE.getName()),
                        new MoveRequestDTO("P2", MovementConstants.PAPER.getName()),
                        new MoveRequestDTO("P3", MovementConstants.SPOCK.getName())
                )
        );
    }

    private void movementGroupWithAllEqualsMovements() throws JokenpoException {
        // Movements cleared
        this.moveService.clearAll();
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequestDTO("P1", MovementConstants.LIZARD.getName()),
                        new MoveRequestDTO("P2", MovementConstants.LIZARD.getName()),
                        new MoveRequestDTO("P3", MovementConstants.LIZARD.getName())
                )
        );
    }

    private void movementGroupWithTwoEqualsMovements() throws JokenpoException {
        // Movements cleared
        this.moveService.clearAll();
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequestDTO("P1", MovementConstants.PAPER.getName()),
                        new MoveRequestDTO("P2", MovementConstants.LIZARD.getName()),
                        new MoveRequestDTO("P3", MovementConstants.PAPER.getName())
                )
        );
    }

}
