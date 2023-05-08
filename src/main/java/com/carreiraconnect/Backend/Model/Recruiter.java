package com.carreiraconnect.Backend.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

@Document
public class Recruiter {

    @Id
    private String id;
    private String name;
    private String cpf;
    private String email;
    private String graduation;
    private Date birthDate;
    private String academicArea;
    private String city;
    private Company company;
    @DocumentReference
    private List<Vacancy> vacancyManaged;


    public Recruiter() {
    }

    public Recruiter(String id, String name, String cpf, String email, String graduation, Date birthDate, String academicArea, String city, Company company, List<Vacancy> vacancyManaged) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.graduation = graduation;
        this.birthDate = birthDate;
        this.academicArea = academicArea;
        this.city = city;
        this.company = company;
        this.vacancyManaged = vacancyManaged;
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

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Vacancy> getVacanciesManaged() {
        return vacancyManaged;
    }

    public void setVacanciesManaged(List<Vacancy> vacancyManaged) {
        this.vacancyManaged = vacancyManaged;
    }
}
