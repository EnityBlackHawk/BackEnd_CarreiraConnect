package com.carreiraconnect.Backend.Repository;

import com.carreiraconnect.Backend.Model.Recruiter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruiterRepository extends MongoRepository<Recruiter, String> {
}
