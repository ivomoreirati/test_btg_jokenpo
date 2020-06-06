package br.com.btg.playgames.jokenpo.exception;

import br.com.btg.playgames.jokenpo.constants.MessageException;

public class JokenpoException extends Exception {

    public JokenpoException(MessageException messageException){
        super(messageException.getCode() + " - " + messageException.getMessage());
    }

    public JokenpoException(String errorMessage){
        super(errorMessage);
    }

}
