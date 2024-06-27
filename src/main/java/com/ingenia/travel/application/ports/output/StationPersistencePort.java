package com.ingenia.travel.application.ports.output;

import java.util.List;
import java.util.Optional;

import com.ingenia.travel.domain.model.Station;

public interface StationPersistencePort {

   Optional<Station> findById(Long id);

   Optional<Station> findByName(String name);

   List<Station> findAll();

   Station save(Station station);

   void deleteById(Long id);
}
