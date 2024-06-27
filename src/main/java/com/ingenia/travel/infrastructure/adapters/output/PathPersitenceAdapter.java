package com.ingenia.travel.infrastructure.adapters.output;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ingenia.travel.application.ports.output.PathPersistencePort;
import com.ingenia.travel.domain.model.Path;
import com.ingenia.travel.infrastructure.adapters.output.persistence.mapper.PathPersistenceMapper;
import com.ingenia.travel.infrastructure.adapters.output.persistence.repository.PathRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PathPersitenceAdapter implements PathPersistencePort {

   private final PathRepository pathRepository;

   private final PathPersistenceMapper pathPersistenceMapper;

   @Override
   public Optional<Path> findById(Long id) {
      return pathRepository.findById(id).map(pathPersistenceMapper::toPath);
   }

   @Override
   public Optional<Path> findByStationIds(Long sourceId, Long destinationId) {
      return pathRepository.findByStationIds(sourceId, destinationId).map(pathPersistenceMapper::toPath);
   }

   @Override
   public List<Path> findBySourceId(Long sourceId) {
      return pathPersistenceMapper.toPaths(pathRepository.findByStartStationId(sourceId));
   }

   @Override
   public List<Path> findAll() {
      return pathPersistenceMapper.toPaths(pathRepository.findAll());
   }

   @Override
   public Path save(Path path) {
      return pathPersistenceMapper.toPath(pathRepository.save(pathPersistenceMapper.toPathEntity(path)));
   }

   @Override
   public void deleteById(Long id) {

   }
}
