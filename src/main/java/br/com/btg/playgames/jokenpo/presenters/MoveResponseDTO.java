package br.com.btg.playgames.jokenpo.presenters;

import br.com.btg.playgames.jokenpo.constants.MovementConstants;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class MoveResponseDTO {

    @NotNull(message = "Player is required")
    private PlayerResponseDTO player;

    @NotNull(message = "Movement is required")
    private MovementConstants movement;

    public MoveResponseDTO(){
    }

    public MoveResponseDTO(PlayerResponseDTO player, MovementConstants movement) {
        this.player = player;
        this.movement = movement;
    }

    public PlayerResponseDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerResponseDTO player) {
        this.player = player;
    }

    public MovementConstants getMovement() {
        return movement;
    }

    public void setMovement(MovementConstants movement) {
        this.movement = movement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveResponseDTO that = (MoveResponseDTO) o;
        return Objects.equals(player, that.player) &&
                movement == that.movement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, movement);
    }

    @Override
    public String toString() {
        return "MoveResponse{" +
                "player=" + player +
                ", movement=" + movement +
                '}';
    }

}
