package com.carreiraconnect.Backend;

public class Response<T> {
    private T data;
    private int report;
    private String message;

    public Response(T data, Error error)
    {
        this.data = data;
        report = error.toInt();
        message = error.toString();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
