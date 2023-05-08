package com.carreiraconnect.Backend.Repository;

import com.carreiraconnect.Backend.Model.Credentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsRepository extends MongoRepository<Credentials, String> {

}
