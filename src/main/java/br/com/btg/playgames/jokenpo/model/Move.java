package br.com.btg.playgames.jokenpo.model;

import br.com.btg.playgames.jokenpo.constants.MovementConstants;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Move {

    private Player player;
    private MovementConstants movementConstants;

    public Move(Player player, MovementConstants movementConstants) {
        this.player = player;
        this.movementConstants = movementConstants;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MovementConstants getMovementConstants() {
        return movementConstants;
    }

    public void setMovementConstants(MovementConstants movementConstants) {
        this.movementConstants = movementConstants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Move that = (Move) o;
        return Objects.equals(player, that.player) &&
                Objects.equals(movementConstants, that.movementConstants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player, movementConstants);
    }

    @Override
    public String toString() {
        return "MoveEntity{" +
                "player=" + player +
                ", enumMovement=" + movementConstants +
                '}';
    }

}
