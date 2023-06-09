package com.carreiraconnect.Backend.DTO;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class Candidate_VacancyDTO {
    @Id
    private String id;
    private String name;
    private String cpf;
    private String email;
    private List<String> abilities;
    private Integer period;
    private Date birthDate;
    private String academicArea;
    private String city;

    public Candidate_VacancyDTO() {
    }

    public Candidate_VacancyDTO(String id, String name, String cpf, String email, List<String> abilities, Integer period, Date birthDate, String academicArea, String city) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.abilities = abilities;
        this.period = period;
        this.birthDate = birthDate;
        this.academicArea = academicArea;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAcademicArea() {
        return academicArea;
    }

    public void setAcademicArea(String academicArea) {
        this.academicArea = academicArea;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
