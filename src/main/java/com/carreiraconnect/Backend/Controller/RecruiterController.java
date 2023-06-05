package com.carreiraconnect.Backend.Controller;

import com.carreiraconnect.Backend.DTO.RecruiterRegisterDTO;
import com.carreiraconnect.Backend.Error;
import com.carreiraconnect.Backend.Model.Company;
import com.carreiraconnect.Backend.Model.Credentials;
import com.carreiraconnect.Backend.Model.Recruiter;
import com.carreiraconnect.Backend.Repository.RecruiterRepository;
import com.carreiraconnect.Backend.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "api/recruiter")
@Api(tags = "Recruiter")
public class RecruiterController{

    @Autowired
    private RecruiterRepository repository;

    @Autowired
    private CredentialsController credentialsController;

    @GetMapping(value = "/add/Test")
    @ApiOperation(value = "Inserts a new recruiter with predefined attributes")
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

        var cred = new Credentials();
        cred.setPassword("1234");
        cred.setId(o.getEmail());
        cred.setRecruiterRef(o);

        if(!credentialsController.Insert(cred))
        {
            Delete(o);
            return "Error";
        }

        return "OK";
    }

    @GetMapping(value = "/getAll")
    @ApiOperation(value = "Returns all recruiters registered")
    public ResponseEntity<Response<List<Recruiter>>> GetAll()
    {
        var o = repository.findAll();
        return ResponseEntity.ok(new Response<>(o, Error.OK));
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "Inserts a new recruiter according to the attributes sent in the request body JSON")
    public ResponseEntity<Response<Void>> Insert(@RequestBody RecruiterRegisterDTO recruiterRegisterDTO)
    {
        repository.insert(recruiterRegisterDTO);
        var cred = new Credentials();
        cred.setId(recruiterRegisterDTO.getEmail());
        cred.setRecruiterRef(recruiterRegisterDTO);
        cred.setPassword(recruiterRegisterDTO.getPassword());
        if(!credentialsController.Insert(cred))
        {
            Delete(recruiterRegisterDTO);
            return ResponseEntity.ok(new Response<>(null, Error.GENERIC_ERROR));
        }
        return ResponseEntity.ok(new Response<>(null, Error.OK));

    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "Delete a recruiter according to the attributes sent in the request body JSON")
    public ResponseEntity<Response<Void>> Delete(@RequestBody Recruiter recruiter)
    {
        repository.delete(recruiter);
        return ResponseEntity.ok(new Response<>(null, Error.OK));
    }
}
