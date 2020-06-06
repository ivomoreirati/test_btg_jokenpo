package br.com.btg.playgames.jokenpo.parameters;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class MoveRequestDTO {

    @Size(min = 1)
    @NotNull( message = "Player name is required")
    private String playerName;

    @Size(min = 1)
    @NotNull( message = "Movement is required")
    private String movement;

    public MoveRequestDTO(String playerName, String movement) {
        this.playerName = playerName;
        this.movement = movement;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveRequestDTO that = (MoveRequestDTO) o;
        return Objects.equals(playerName, that.playerName) &&
                Objects.equals(movement, that.movement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, movement);
    }

    @Override
    public String toString() {
        return "MoveRequest{" +
                "playerName='" + playerName + '\'' +
                ", movement='" + movement + '\'' +
                '}';
    }

}
