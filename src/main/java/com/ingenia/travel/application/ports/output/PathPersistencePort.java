package com.ingenia.travel.application.ports.output;

import java.util.List;
import java.util.Optional;

import com.ingenia.travel.domain.model.Path;

public interface PathPersistencePort {

   Optional<Path> findById(Long id);

   Optional<Path> findByStationIds(Long sourceId, Long destinationId);

   List<Path> findBySourceId(Long sourceId);

   List<Path> findAll();

   Path save(Path path);

   void deleteById(Long id);
}
