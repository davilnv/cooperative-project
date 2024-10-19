package br.com.davilnv.cooperative.domain.exception;

public class TheresAlreadyOpenVotingSessionException extends Exception {
    public TheresAlreadyOpenVotingSessionException(String message) {
        super(message);
    }
}
