package org.igesta.repository;

import org.bson.types.ObjectId;
import org.igesta.model.Registro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RegistroRepository extends MongoRepository<Registro, String> {

    List<Registro> findByDataBetween(Date dataInicio, Date dataFim);

}

