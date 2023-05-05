package com.carreiraconnect.Backend.Controller;

import com.carreiraconnect.Backend.Error;
import com.carreiraconnect.Backend.Model.Candidate;
import com.carreiraconnect.Backend.ModelUpdater;
import com.carreiraconnect.Backend.Repository.CandidateRepository;
import com.carreiraconnect.Backend.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/candidate")
public class CandidateController {

    @Autowired
    private CandidateRepository repository;

    @PostMapping(value = "/add/Test")
    public String InsertTest()
    {
        return "None";
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Response<Void>> Insert(@RequestBody Candidate candidate)
    {
        repository.insert(candidate);
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
