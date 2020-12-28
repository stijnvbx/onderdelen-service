package com.example.onderdelen.repository;

import com.example.onderdelen.model.Onderdeel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnderdeelRepository extends MongoRepository<Onderdeel, String> {

    List<Onderdeel> findOnderdeelsByMerk(String merk);

    Onderdeel findOnderdeelByMerkAndNaam(String merk, String naam);
}
