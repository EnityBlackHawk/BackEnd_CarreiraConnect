package com.carreiraconnect.Backend.Model;

public class Company {

    private String name;
    private String cnpj;
    private String description;

    public Company() {
    }

    public Company(String name, String cnpj, String description) {
        this.name = name;
        this.cnpj = cnpj;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
