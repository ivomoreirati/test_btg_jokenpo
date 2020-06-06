package br.com.btg.playgames.jokenpo.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class PlayerRequestDTO {

    @NotNull( message = "Player name is required" )
    @JsonProperty(value = "playerName")
    private String name;

    public PlayerRequestDTO(){
    }

    public PlayerRequestDTO(String name) {
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
        PlayerRequestDTO that = (PlayerRequestDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "PlayerRequest{" +
                "name='" + name + '\'' +
                '}';
    }

}
