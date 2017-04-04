package com.cremy.firebucket.data.exceptions;

public class NetworkConnectionException extends Exception {

    public NetworkConnectionException() {
        super();
    }

    public NetworkConnectionException(String message) {
        super(message);
    }

    public NetworkConnectionException(final Throwable cause) {
        super(cause);
    }
}