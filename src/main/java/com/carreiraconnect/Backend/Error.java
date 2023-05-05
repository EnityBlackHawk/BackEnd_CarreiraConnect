package com.carreiraconnect.Backend;

public enum Error {

    OK(0),
    OBJECT_NOT_FOUND(1),
    ALREADY_APPLIED(2);

    private final int i;

    Error(int i) {
        this.i = i;
    }

    public int toInt()
    {
        return i;
    }
}
