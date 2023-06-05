package com.carreiraconnect.Backend.Controller;

import com.carreiraconnect.Backend.DTO.VacancyDTO;
import com.carreiraconnect.Backend.Error;
import com.carreiraconnect.Backend.Model.Candidate;
import com.carreiraconnect.Backend.Model.Recruiter;
import com.carreiraconnect.Backend.Model.Vacancy;
import com.carreiraconnect.Backend.Repository.CandidateRepository;
import com.carreiraconnect.Backend.Repository.RecruiterRepository;
import com.carreiraconnect.Backend.Repository.VacanciesRepository;
import com.carreiraconnect.Backend.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/vacancies")
@Api(tags = "Vacancies")
public class VacanciesController {

    @Autowired
    private VacanciesRepository repository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @PostMapping(value = "/add/Test/{id}")
    @ApiOperation(value = "Inserts a new vacancy with predefined attributes and the id sent in the request body")
    public String Test(@RequestBody Recruiter recruiter) {
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

        try {
            var result = repository.insert(o);
        } catch (DataIntegrityViolationException e) {
            return "Erro";
        }
        return "OK";
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "Inserts a new vacancy")
    public ResponseEntity<Response<Void>> Add(@RequestBody VacancyDTO vacancy)
    {
        ModelMapper mm = new ModelMapper();
        var m_v = mm.map(vacancy, Vacancy.class);
        var r = recruiterRepository.findById(vacancy.getRecruiter().getId()).orElseThrow();
        m_v = repository.save(m_v);

        if(r.getVacanciesManaged() == null)
            r.setVacanciesManaged(new ArrayList<>());
        r.getVacanciesManaged().add(m_v);

        recruiterRepository.save(r);

        return ResponseEntity.ok(new Response<>(null, Error.OK));
    }

    @PostMapping(value = "/apply/{id}")
    @ApiOperation(value = "Applies for the candidate sent in the requisition body to the job id passed in the requisition")
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
    @ApiOperation(value = "Returns all the vacancies registered")
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

    @GetMapping(value = "/getBySalaryRanged")
    @ApiOperation(value = "Returns all the vacancies by salary range according to the parameter passed in the request")
    public ResponseEntity<Response<List<VacancyDTO>>> getBySalaryRange(@RequestParam(required = true) String from, @RequestParam(required = false) String to)
    {
        float f_from = Float.parseFloat(from);
        float f_to = (to == null || to.isBlank() || to.isEmpty()) ? Float.MAX_VALUE : Float.parseFloat(to);

        var ret = new ArrayList<VacancyDTO>();
        var result = repository.findAll().stream().filter((vacancy ->
        {
            var s = vacancy.getSalary();
            return f_from <= s && s <= f_to;
        })).toList();

        ModelMapper modelMapper = new ModelMapper();
        for(var v : result)
            ret.add(
                    modelMapper.map(v, VacancyDTO.class)
            );

        return ResponseEntity.ok(new Response<>(ret, Error.OK));

    }

    @GetMapping(value = "/getAllByInterest")
    @ApiOperation(value = "Returns all vacancies by interest or description according to the description in the request")
    public ResponseEntity<Response<List<VacancyDTO>>> getAllVacanciesByInterest(
            @RequestParam("description") String description) {

        if (description != null && description.length() > 0) {
            List<Vacancy> vacancyList = repository.findAllByInterest(description);

            ArrayList<VacancyDTO> arrayListVacancies = new ArrayList<>();

            if (vacancyList != null && vacancyList.size() > 0) {
                ModelMapper modelMapper = new ModelMapper();

                 arrayListVacancies = (ArrayList<VacancyDTO>) vacancyList.stream()
                        .map(listVacancies -> modelMapper.map(listVacancies, VacancyDTO.class))
                        .collect(Collectors.toList());
            }
            return ResponseEntity.ok(new Response<>(arrayListVacancies, Error.OK));
        } else {
            Response<List<VacancyDTO>> response = new Response<>(null, Error.BAD_REQUEST);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping(value = "/getAllByTechnology")
    @ApiOperation(value = "Returns all vacancies by technology according to the categories sent in the request")
    public ResponseEntity<Response<List<VacancyDTO>>> getAllVacanciesByTechnology(
            @RequestParam("categories") List<String> categories) {

        ArrayList<VacancyDTO> arrayListVacancies;

        ModelMapper modelMapper = new ModelMapper();

        if (categories != null && categories.size() > 0) {
            List<Vacancy> vacancyList = repository.findAllByCategories(categories);

            arrayListVacancies = vacancyList != null && vacancyList.size() > 0 ?
                    (ArrayList<VacancyDTO>) vacancyList.stream()
                            .map(listVacancies ->  modelMapper.map(listVacancies, VacancyDTO.class))
                            .collect(Collectors.toList()) : null;
            return ResponseEntity.ok(new Response<>(arrayListVacancies, Error.OK));
        } else {
            Response<List<VacancyDTO>> response = new Response<>(null, Error.BAD_REQUEST);
            return ResponseEntity.badRequest().body(response);
        }
    }

}
