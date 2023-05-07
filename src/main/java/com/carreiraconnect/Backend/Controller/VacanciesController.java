package com.carreiraconnect.Backend.Controller;

import com.carreiraconnect.Backend.Error;
import com.carreiraconnect.Backend.Model.Candidate;
import com.carreiraconnect.Backend.Model.Recruiter;
import com.carreiraconnect.Backend.Model.Vacancy;
import com.carreiraconnect.Backend.Repository.CandidateRepository;
import com.carreiraconnect.Backend.Repository.VacanciesRepository;
import com.carreiraconnect.Backend.Response;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@RestController
@RequestMapping(value = "api/vacancies")
public class VacanciesController {

    @Autowired
    private VacanciesRepository repository;

    @Autowired
    private CandidateRepository candidateRepository;

    @GetMapping(value = "/stats/{id}")
    public ResponseEntity<Response<List<String>>> stats(@PathVariable(value = "id") String id){
        var vac_stats = new ArrayList<String>();
        float engagement;
        var vacancy = repository.findById(id);

        if(vacancy.isPresent()){
            //Coverter valores NULL para 0
            var viewCont = vacancy.get().getViewCont();
            var candidates = vacancy.get().getCandidates().size();
            if(Objects.isNull(viewCont)){viewCont = 0;}
            Consumer<? super Vacancy> sv;
            vac_stats.add(String.valueOf(viewCont));
            vac_stats.add(String.valueOf(candidates));
            //Métrica de Engajamento = ViewCount/Candidates
            engagement =  (float) viewCont / candidates;
            vac_stats.add(String.valueOf(engagement));
            return ResponseEntity.ok(new Response<>(vac_stats, Error.OK));
        }
        return ResponseEntity.ok(new Response<>(vac_stats, Error.OK));
    }

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
        o.setViewCont(0);
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
    public ResponseEntity<Response<List<String>>> getAll()
    {
        var unfiltered_vacancies = repository.findAll();
        var vacancies = new ArrayList<String>();
        Consumer<? super Vacancy> sv;
        unfiltered_vacancies.forEach((v) -> {
            vacancies.add(v.getId());
            vacancies.add(v.getDescription());
            vacancies.add(v.getModality());
            vacancies.add(String.valueOf(v.getWorkload()));
            vacancies.add(String.valueOf(v.getSalary()));
    });
        return ResponseEntity.ok(new Response<>(vacancies, Error.OK));
    }



}
