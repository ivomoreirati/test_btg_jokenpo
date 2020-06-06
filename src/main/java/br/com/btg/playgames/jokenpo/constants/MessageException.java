package br.com.btg.playgames.jokenpo.constants;

public enum MessageException {

    GENERIC_ERROR("ERR-0001", "GENERIC ERROR", "GENERIC ERROR", "Generic error"),
    INVALID_PARAM("ERR-0002", "PARAM", "INVALID", "Invalid parameter"),

    // PLAYER
    PLAYER_NOT_FOUND("ERR-1001", "PLAYER", "NOT FOUND", "Player not found"),
    PLAYER_ALREADY_EXISTS("ERR-1002", "PLAYER", "ALREADY EXISTS", "Player already registered"),
    PLAYER_INVALID_NAME("ERR-1003", "PLAYER", "NAME", "Invalid player name"),
    PLAYER_SAVE_ERROR("ERR-1004", "PLAYER", "SAVE", "Error saving the player"),
    PLAYER_DELETE_ERROR("ERR-1005", "PLAYER", "SAVE", "Error deleting the player"),
    PLAYER_FIND_ALL_ERROR("ERR-1006", "PLAYER", "FIND ALL", "Error at looking for the players"),

    // MOVEMENT
    MOVEMENT_NOT_FOUND("ERR-2001", "MOVEMENT", "NOT FOUND", "Movement not found"),
    MOVEMENT_ALREADY_EXISTS("ERR-2002", "MOVEMENT", "ALREADY EXISTS", "This player has played before"),
    MOVEMENT_INVALID("ERR-2003", "MOVEMENT", "INVALID", "Invalid movement"),
    MOVEMENT_SAVE_ERROR("ERR-2004", "MOVEMENT", "SAVE", "Error saving"),
    MOVEMENT_DELETE_ERROR("ERR-2005", "MOVEMENT", "SAVE", "Error deleting"),
    MOVEMENT_FIND_ALL_ERROR("ERR-2006", "MOVEMENT", "FIND ALL", "Error locating movements"),

    // JOCKENPO - PLAY
    NOBODY_PLAYING("ERR-3001", "PLAY", "NOBODY", "There's no one playing"),
    INSUFFICIENT_PLAYERS("ERR-3002", "PLAY", "INSUFFICIENT PLAYERS", "Insufficient number of players"),
    INSUFFICIENT_MOVEMENTS("ERR-3002", "PLAY", "INSUFFICIENT MOVEMENTS", "Number of movements still insufficient"),
    PLAYERS_PENDING("ERR-3003", "PLAY", "PLAYERS PENDING", "There are players who have not yet chosen");

    private final String code;
    private final String origin;
    private final String type;
    private final String subType;
    private String message;

    MessageException(String code, String type, String subType, String message) {
        this.code = code;
        this.origin = "JOKENPO";
        this.type = type;
        this.subType = subType;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getOrigin() {
        return origin;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static MessageException getEnumExceptionByCode(String code){
        for (MessageException elem : MessageException.values()) {
            if (code.equals(elem.getCode())) {
                return elem;
            }
        }
        return MessageException.GENERIC_ERROR;
    }

}
