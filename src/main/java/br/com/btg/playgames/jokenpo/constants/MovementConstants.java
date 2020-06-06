package br.com.btg.playgames.jokenpo.constants;

import br.com.btg.playgames.jokenpo.exception.JokenpoException;

import java.util.List;

import static java.util.Arrays.asList;

public enum MovementConstants {

    SPOCK("SPOCK"),
    SCISSORS("SCISSORS"),
    PAPER("PAPER"),
    STONE("STONE"),
    LIZARD("LIZARD");

    private String name;
    private List<MovementConstants> weakness;

    static {
        SPOCK.setWeakness(asList(LIZARD, PAPER));
        SCISSORS.setWeakness(asList(SPOCK, STONE));
        PAPER.setWeakness(asList(SCISSORS, LIZARD));
        STONE.setWeakness(asList(SPOCK, PAPER));
        LIZARD.setWeakness(asList(SCISSORS, STONE));
    }

    MovementConstants(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovementConstants> getWeakness() {
        return weakness;
    }

    public void setWeakness(List<MovementConstants> weakness) {
        this.weakness = weakness;
    }

    public static MovementConstants getEnumMovementByName(String name) throws JokenpoException {
        for (MovementConstants elem : MovementConstants.values()) {
            if (name.equals(elem.getName())) {
                return elem;
            }
        }
        throw new JokenpoException(MessageException.MOVEMENT_NOT_FOUND);
    }

}
