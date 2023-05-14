package com.carreiraconnect.Backend;

public enum Error {

    OK(0),
    BAD_REQUEST(400),
    OBJECT_NOT_FOUND(1),
    ALREADY_APPLIED(2),
    EMAIL_ALREADY_USED(3),
    GENERIC_ERROR(4);

    private final int i;

    Error(int i) {
        this.i = i;
    }

    public int toInt()
    {
        return i;
    }
}
