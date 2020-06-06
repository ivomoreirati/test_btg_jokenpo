package br.com.btg.playgames.jokenpo.util;

import br.com.btg.playgames.jokenpo.model.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PlayerUtils {

    private static List<Player> PLAYER_INSTANCE;

    private PlayerUtils(){
    }

    public static List<Player> getInstance() {
        if(PLAYER_INSTANCE == null) {
            PLAYER_INSTANCE = new ArrayList<>();
        }
        return PLAYER_INSTANCE;
    }

    public static List<Player> clear(){
        PLAYER_INSTANCE = new ArrayList<>();
        return getInstance();
    }

}
