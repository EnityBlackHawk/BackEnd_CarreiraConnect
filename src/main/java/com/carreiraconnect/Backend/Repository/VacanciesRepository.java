package com.carreiraconnect.Backend.Repository;

import com.carreiraconnect.Backend.Model.Vacancy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@Repository
public interface VacanciesRepository extends MongoRepository<Vacancy, String> {
    @Query("{ 'description': { '$regex': ?0, '$options': 'i' } }")
    List<Vacancy> findAllByDescription(String description);
}
