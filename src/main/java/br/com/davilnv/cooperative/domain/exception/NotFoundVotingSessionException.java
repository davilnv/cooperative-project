package br.com.davilnv.cooperative.domain.exception;

public class NotFoundVotingSessionException extends Exception {
    public NotFoundVotingSessionException(String message) {
        super(message);
    }
}
