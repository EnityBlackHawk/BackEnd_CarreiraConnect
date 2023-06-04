package com.carreiraconnect.Backend.Controller;

import com.carreiraconnect.Backend.DTO.RecruiterRegisterDTO;
import com.carreiraconnect.Backend.Error;
import com.carreiraconnect.Backend.Model.Company;
import com.carreiraconnect.Backend.Model.Credentials;
import com.carreiraconnect.Backend.Model.Recruiter;
import com.carreiraconnect.Backend.Model.Vacancy;
import com.carreiraconnect.Backend.Repository.CandidateRepository;
import com.carreiraconnect.Backend.Repository.RecruiterRepository;
import com.carreiraconnect.Backend.Repository.VacanciesRepository;
import com.carreiraconnect.Backend.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

@RestController
@RequestMapping(value = "api/recruiter")
public class RecruiterController{

    @Autowired
    private RecruiterRepository repository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VacanciesRepository vacanciesRepository;

    @GetMapping(value = "/stats/registeredCandidates")
    public ResponseEntity<Response<Long>> candidateStats(){
        var c = candidateRepository.count();
        return ResponseEntity.ok(new Response<>(c, Error.OK));
    }

    @GetMapping(value = "/stats/vacancyStats/{id}")
    public ResponseEntity<Response<List<String>>> stats(@PathVariable(value = "id") String id){
        var vac_stats = new ArrayList<String>();
        float engagement;
        var vacancy = vacanciesRepository.findById(id);

        if(vacancy.isPresent()){
            //Coverter valores NULL para 0
            var viewCont = vacancy.get().getViewCont();
            var candidates = vacancy.get().getCandidates().size();
            if(isNull(viewCont)){viewCont = 0;}
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
    @Autowired
    private CredentialsController credentialsController;

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
    public ResponseEntity<Response<List<Recruiter>>> GetAll()
    {
        var o = repository.findAll();
        return ResponseEntity.ok(new Response<>(o, Error.OK));
    }
    @PostMapping(value = "/add")
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
    public ResponseEntity<Response<Void>> Delete(@RequestBody Recruiter recruiter)
    {
        repository.delete(recruiter);
        return ResponseEntity.ok(new Response<>(null, Error.OK));
    }


}
