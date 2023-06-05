package com.carreiraconnect.Backend.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

public class Credentials {

    @Id
    private String id;
    private String password;

    private Boolean isCandidate;
    @Field
    @DBRef
    private Candidate candidateRef;
    @Field
    @DBRef
    private Recruiter recruiterRef;


    public Credentials() {
    }

    public Credentials(String id, String password, Boolean isCandidate, Candidate candidateRef, Recruiter recruiterRef) {
        this.id = id;
        this.password = password;
        this.isCandidate = isCandidate;
        this.candidateRef = candidateRef;
        this.recruiterRef = recruiterRef;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getCandidate() {
        return isCandidate;
    }

    public void setCandidate(Boolean candidate) {
        isCandidate = candidate;
    }

    public Candidate getCandidateRef() {
        return candidateRef;
    }

    public void setCandidateRef(Candidate candidateRef) {
        this.candidateRef = candidateRef;
    }

    public Recruiter getRecruiterRef() {
        return recruiterRef;
    }

    public void setRecruiterRef(Recruiter recruiterRef) {
        this.recruiterRef = recruiterRef;
    }
}
