package com.carreiraconnect.Backend.Controller;

import com.carreiraconnect.Backend.Error;
import com.carreiraconnect.Backend.Model.Credentials;
import com.carreiraconnect.Backend.Repository.CredentialsRepository;
import com.carreiraconnect.Backend.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/credentials")
@Api(tags = "Credentials")
public class CredentialsController {

    @Autowired
    private CredentialsRepository repository;

    @PostMapping(value = "/verifyEmail")
    @ApiOperation(value = "Validate if the email sent in the request body JSON already exists")
    public ResponseEntity<Response<Void>> verifyEmail_request(@RequestBody String email)
    {
        return ResponseEntity.ok(new Response<>(null, repository.findById(email).isPresent() ? Error.OK : Error.EMAIL_ALREADY_USED));
    }

    public Boolean VerifyEmail(String email)
    {
        return repository.findById(email).isPresent();
    }

    @ApiOperation(value = "Inserts a new credentials")
    public boolean Insert(Credentials credentials)
    {
        try{
            repository.insert(credentials);
            return true;
        }catch (Exception e)
        {
            return false;
        }

    }

}
