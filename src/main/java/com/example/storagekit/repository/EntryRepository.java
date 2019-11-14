package com.example.storagekit.repository;

import com.example.storagekit.model.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntryRepository extends MongoRepository<Entry, String> {

    Optional<Entry> findByKey(String key);
}
