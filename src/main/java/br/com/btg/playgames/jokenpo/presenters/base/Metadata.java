package br.com.btg.playgames.jokenpo.presenters.base;

import java.sql.Timestamp;

public class Metadata {

    private Timestamp timestamp;

    public Metadata(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
