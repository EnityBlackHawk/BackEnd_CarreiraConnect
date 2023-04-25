package com.carreiraconnect.Backend.Repository;

import com.carreiraconnect.Backend.Model.Candidate;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {
    void deleteById(ObjectId objectId);
}
