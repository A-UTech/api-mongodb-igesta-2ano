package org.igesta.repository;

import org.bson.types.ObjectId;
import org.igesta.model.Registro;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistroRepository extends MongoRepository<Registro, String> {
}
