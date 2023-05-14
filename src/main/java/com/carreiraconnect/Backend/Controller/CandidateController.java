package com.carreiraconnect.Backend.Controller;

import com.carreiraconnect.Backend.DTO.CandidateRegisterDTO;
import com.carreiraconnect.Backend.Error;
import com.carreiraconnect.Backend.Model.Candidate;
import com.carreiraconnect.Backend.Model.Credentials;
import com.carreiraconnect.Backend.ModelUpdater;
import com.carreiraconnect.Backend.Repository.CandidateRepository;
import com.carreiraconnect.Backend.Response;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/candidate")
public class CandidateController {

    @Autowired
    private CandidateRepository repository;

    @Autowired
    CredentialsController credentialsController;

    public long getCandidates(){
        return repository.count();
    }

    @GetMapping(value = "/stats/RegisteredCandidates")
    public ResponseEntity<Response<Long>> candidateStats(){
        var c = repository.count();
        return ResponseEntity.ok(new Response<>(c, Error.OK));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<Response<ArrayList<String>>> getAll()
    {
        var unfiltered_candidates = repository.findAll();
        var candidates = new ArrayList<String>();

        unfiltered_candidates.forEach((c) -> {
            candidates.add(c.getId());
            candidates.add(c.getName());
            candidates.add(c.getAcademicArea());
            candidates.add(String.valueOf(c.getPeriod()));
            candidates.add(c.getEmail());
            candidates.add(c.getCpf());
        });

        return ResponseEntity.ok(new Response<>(candidates, Error.OK));
    }

    @PostMapping(value = "/add/Test")
    public String InsertTest()
    {


        var c = new Candidate();
        c.setName("Teste Senha abs");
        c.setEmail("1234@mail.com");

        if(credentialsController.VerifyEmail(c.getEmail()))
        {
            return "Email error";
        }

        var resp = repository.insert(c);

        var cred = new Credentials();
        cred.setId("1234@mail.com");
        cred.setPassword("1234");
        cred.setCandidateRef(resp);
        cred.setCandidate(true);

        credentialsController.Insert(cred);

        return "None";
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Response<Void>> Insert(@RequestBody CandidateRegisterDTO candidateDTO)
    {
        var modelMapper = new ModelMapper();
        var candidate = modelMapper.map(candidateDTO, Candidate.class);

        repository.insert(candidate);

        var cred = new Credentials();
        cred.setId(candidate.getEmail());
        cred.setPassword(candidateDTO.getPassword());
        cred.setCandidateRef(candidate);
        cred.setCandidate(true);

        if(!credentialsController.Insert(cred))
        {
            Delete(candidate.getId());
            return ResponseEntity.ok(new Response<>(null, Error.GENERIC_ERROR));
        }

        return ResponseEntity.ok(new Response<>(null, Error.OK));
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Response<Void>> Delete(@PathVariable String id)
    {
        var oid = new ObjectId(id);
        repository.deleteById(oid);

        return ResponseEntity.ok(new Response<>(null, Error.OK));
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Response<Void>> Update(@RequestBody Candidate candidate)
    {
        Optional<Candidate> base = repository.findById(candidate.getId());

        if(base.isEmpty())
            return ResponseEntity.ok(new Response<>(null, Error.OBJECT_NOT_FOUND));

        ModelUpdater.Update2(base.get(), candidate);

        repository.save(base.get());
        return ResponseEntity.ok(new Response<>(null, Error.OK));
    }

}
