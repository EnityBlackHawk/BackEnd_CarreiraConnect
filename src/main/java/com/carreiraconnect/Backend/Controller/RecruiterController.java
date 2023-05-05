package com.carreiraconnect.Backend.Controller;

import com.carreiraconnect.Backend.Error;
import com.carreiraconnect.Backend.Model.Company;
import com.carreiraconnect.Backend.Model.Recruiter;
import com.carreiraconnect.Backend.Repository.RecruiterRepository;
import com.carreiraconnect.Backend.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "api/recruiter")
public class RecruiterController{

    @Autowired
    private RecruiterRepository repository;

    @GetMapping(value = "/add/Test")
    public String Test()
    {
        var o = new Recruiter();
        o.setName("Maria2");
        o.setCpf("000.000.000-00");
        o.setEmail("user@mail.com");
        o.setBirthDate(new Date());
        o.setAcademicArea("Engenharia de Software");
        o.setCity("Dois Vizinhos-PR");
        o.setCompany(new Company("Empresa S/A", "000000000", "Soluções em software"));
        repository.insert(o);
        return "OK";
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<Response<List<Recruiter>>> GetAll()
    {
        var o = repository.findAll();
        return ResponseEntity.ok(new Response<>(o, Error.OK));
    }


}
