package br.com.btg.playgames.jokenpo.util;

import br.com.btg.playgames.jokenpo.model.Move;

import java.util.ArrayList;
import java.util.List;

public final class MoveUtils {

    private static List<Move> MOVE_INSTANCE;

    private MoveUtils(){
    }

    public static List<Move> getInstance() {
        if(MOVE_INSTANCE == null) {
            MOVE_INSTANCE = new ArrayList<>();
        }
        return MOVE_INSTANCE;
    }

    public static List<Move> clear(){
        MOVE_INSTANCE = new ArrayList<>();
        return getInstance();
    }

}
