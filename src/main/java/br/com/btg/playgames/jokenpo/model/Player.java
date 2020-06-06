package br.com.btg.playgames.jokenpo.model;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Player {

    private String name;

    public Player(){
    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Player that = (Player) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "PlayerEntity{" +
                "name='" + name + '\'' +
                '}';
    }

}
