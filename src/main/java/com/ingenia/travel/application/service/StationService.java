package com.ingenia.travel.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ingenia.travel.application.ports.input.StationServicePort;
import com.ingenia.travel.application.ports.output.StationPersistencePort;
import com.ingenia.travel.domain.exception.StationDuplicateException;
import com.ingenia.travel.domain.exception.StationNotFoundException;
import com.ingenia.travel.domain.model.Station;
import com.ingenia.travel.infrastructure.adapters.output.persistence.mapper.StationPersistenceMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StationService implements StationServicePort {

   private final StationPersistencePort stationPersistencePort;

   private final StationPersistenceMapper stationPersistenceMapper;

   @Override
   public Station findById(Long id) {
      return stationPersistencePort.findById(id).orElseThrow(StationNotFoundException::new);
   }

   @Override
   public List<Station> findAll() {
      return stationPersistencePort.findAll();
   }

   @Override
   public Station save(Station station) {
      if (!stationPersistencePort.findByName(station.getName()).isEmpty()) {
         throw new StationDuplicateException();
      }
      return stationPersistencePort.save(station);
   }

   @Override
   public Station update(Long id, Station station) {
      return stationPersistencePort.findById(id).map(savedRoad -> {
         savedRoad.setName(station.getName());
         return stationPersistencePort.save(savedRoad);
      }).orElseThrow(StationNotFoundException::new);
   }

   @Override
   public void deleteById(Long id) {
      if (stationPersistencePort.findById(id).isEmpty()) {
         throw new StationNotFoundException();
      }
      stationPersistencePort.deleteById(id);
   }
}
