package com.carreiraconnect.Backend.Controller;

import com.carreiraconnect.Backend.DTO.VacancyDTO;
import com.carreiraconnect.Backend.Error;
import com.carreiraconnect.Backend.Model.Candidate;
import com.carreiraconnect.Backend.Model.Recruiter;
import com.carreiraconnect.Backend.Model.Vacancy;
import com.carreiraconnect.Backend.Repository.CandidateRepository;
import com.carreiraconnect.Backend.Repository.VacanciesRepository;
import com.carreiraconnect.Backend.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "api/vacancies")
public class VacanciesController {

    @Autowired
    private VacanciesRepository repository;

    @Autowired
    private CandidateRepository candidateRepository;

    @PostMapping(value = "/add/Test/{id}")
    public String Test(@RequestBody Recruiter recruiter)
    {
        var o = new Vacancy();
        o.setDescription("Vaga para desenvolvedor");
        o.setWorkload(20);
        o.setSalary(600.00f);
        o.setModality("Hibrido");
        var arr = new ArrayList<String>();
        arr.add("HTML");
        arr.add("CSS");
        arr.add("JavaScript");
        o.setCategories(arr);
        o.setRecruiter(recruiter);
        try
        {
            var result = repository.insert(o);
        }catch (DataIntegrityViolationException e)
        {
            return "Erro";
        }
        return "OK";
    }

    @PostMapping(value = "/apply/{id}")
    public ResponseEntity<Response<Void>> Apply(@PathVariable String id, @RequestBody Candidate candidate)
    {

        Candidate f_candidate = candidateRepository.findById(candidate.getId()).orElse(null);
        if(f_candidate == null)
            return ResponseEntity.ok(new Response<>(null, Error.OBJECT_NOT_FOUND));
        var vaga = repository.findById(id).orElse(null);

        if(vaga == null)
            return ResponseEntity.ok(new Response<>(null, Error.OBJECT_NOT_FOUND));

        var applied = f_candidate.getVacanciesApplied();
        if(applied.stream().anyMatch(v -> Objects.equals(v.getId(), vaga.getId())))
            return ResponseEntity.ok(new Response<>(null, Error.ALREADY_APPLIED));

        var candidatos = vaga.getCandidates();


        applied.add(vaga);
        candidatos.add(candidate);

        repository.save(vaga);
        candidateRepository.save(f_candidate);

        return ResponseEntity.ok(new Response<>(null, Error.OK));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<Response<List<VacancyDTO>>> GetAll_DTO()
    {
        var l_ob = repository.findAll();
        var modelMapper = new ModelMapper();
        var l_dto = new ArrayList<VacancyDTO>();
        for (var ob : l_ob)
            l_dto.add(
                    modelMapper.map(ob, VacancyDTO.class)
            );


        return ResponseEntity.ok(new Response<>(l_dto, Error.OK));
    }

}
