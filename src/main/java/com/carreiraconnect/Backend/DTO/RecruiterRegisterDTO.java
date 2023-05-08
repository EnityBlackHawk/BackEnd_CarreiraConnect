package com.carreiraconnect.Backend.DTO;

import com.carreiraconnect.Backend.Model.Company;
import com.carreiraconnect.Backend.Model.Recruiter;
import com.carreiraconnect.Backend.Model.Vacancy;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.util.List;

public class RecruiterRegisterDTO extends Recruiter {

    @Transient
    private String password;

    public RecruiterRegisterDTO() {
    }

    public RecruiterRegisterDTO(String id, String name, String cpf, String email, String graduation, Date birthDate, String academicArea, String city, Company company, List<Vacancy> vacancyManaged, String password) {
        super(id, name, cpf, email, graduation, birthDate, academicArea, city, company, vacancyManaged);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
