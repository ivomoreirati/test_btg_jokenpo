package br.com.btg.playgames.jokenpo.presenters.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

public class BaseDTO<T> {

    private Metadata metadata;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public BaseDTO(T data) {
        this.metadata = new Metadata(new Timestamp(System.currentTimeMillis()));
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

}
