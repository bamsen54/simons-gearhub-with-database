package com.simon.exception;

public class EmailAlreadyTakenException extends RepositoryException {
    public EmailAlreadyTakenException(String msg ) {
        super( msg );
    }
}
