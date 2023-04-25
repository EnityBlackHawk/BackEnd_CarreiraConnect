package com.carreiraconnect.Backend;

public enum Error {

    OK(0);

    private final int i;

    Error(int i) {
        this.i = i;
    }

    public int toInt()
    {
        return i;
    }
}
