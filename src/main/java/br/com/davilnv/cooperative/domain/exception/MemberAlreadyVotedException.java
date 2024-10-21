package br.com.davilnv.cooperative.domain.exception;

public class MemberAlreadyVotedException extends Exception {
    public MemberAlreadyVotedException(String message) {
        super(message);
    }
}
