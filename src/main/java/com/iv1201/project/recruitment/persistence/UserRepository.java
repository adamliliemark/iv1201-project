package com.iv1201.project.recruitment.persistence;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @Cacheable("users")
    Optional<User> findByEmail(String email);
}