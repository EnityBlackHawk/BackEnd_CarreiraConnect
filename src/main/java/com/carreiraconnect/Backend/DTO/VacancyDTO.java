package com.carreiraconnect.Backend.DTO;

import com.carreiraconnect.Backend.Model.Recruiter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

public class VacancyDTO {
    @Id
    private String id;
    private String description;
    private Integer workload;
    private Float salary;
    private String modality;
    private Integer viewCont;
    @DocumentReference
    private List<Candidate_VacancyDTO> candidates;
    @DocumentReference
    private Recruiter recruiter;
    private List<String> categories;

    public VacancyDTO() {
    }

    public VacancyDTO(String id, String description, Integer workload, Float salary, String modality, Integer viewCont, List<Candidate_VacancyDTO> candidates, Recruiter recruiter, List<String> categories) {
        this.id = id;
        this.description = description;
        this.workload = workload;
        this.salary = salary;
        this.modality = modality;
        this.viewCont = viewCont;
        this.candidates = candidates;
        this.recruiter = recruiter;
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWorkload() {
        return workload;
    }

    public void setWorkload(Integer workload) {
        this.workload = workload;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public Integer getViewCont() {
        return viewCont;
    }

    public void setViewCont(Integer viewCont) {
        this.viewCont = viewCont;
    }

    public List<Candidate_VacancyDTO> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate_VacancyDTO> candidates) {
        this.candidates = candidates;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
