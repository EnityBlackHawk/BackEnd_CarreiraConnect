package com.carreiraconnect.Backend.Repository;

import com.carreiraconnect.Backend.Model.Vacancy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacanciesRepository extends MongoRepository<Vacancy, String> {
}
