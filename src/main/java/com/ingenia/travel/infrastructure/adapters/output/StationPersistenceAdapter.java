package com.ingenia.travel.infrastructure.adapters.output;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ingenia.travel.application.ports.output.StationPersistencePort;
import com.ingenia.travel.domain.model.Station;
import com.ingenia.travel.infrastructure.adapters.output.persistence.mapper.StationPersistenceMapper;
import com.ingenia.travel.infrastructure.adapters.output.persistence.repository.StationRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StationPersistenceAdapter implements StationPersistencePort {

   private final StationRepository stationRepository;

   private final StationPersistenceMapper stationPersistenceMapper;

   @Override
   public Optional<Station> findById(Long id) {
      return stationRepository.findById(id).map(stationPersistenceMapper::toStation);
   }

   @Override
   public Optional<Station> findByName(String name) {
      return stationRepository.findByName(name).map(stationPersistenceMapper::toStation);
   }

   @Override
   public List<Station> findAll() {
      return stationPersistenceMapper.toStations(stationRepository.findAll());
   }

   @Override
   public Station save(Station station) {
      return stationPersistenceMapper.toStation(stationRepository.save(stationPersistenceMapper.toStationEntity(station)));
   }

   @Override
   public void deleteById(Long id) {

   }
}
