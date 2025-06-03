package com.bancobase.pruebatecnica.pagos.repository;

import com.bancobase.pruebatecnica.pagos.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByAccount(String account);
}