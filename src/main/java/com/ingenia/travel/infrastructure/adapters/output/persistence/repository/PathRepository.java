package com.ingenia.travel.infrastructure.adapters.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ingenia.travel.infrastructure.adapters.output.persistence.entity.PathEntity;

public interface PathRepository extends JpaRepository<PathEntity, Long> {

   @Query("SELECT p FROM PathEntity p WHERE p.startStation.stationId = :sourceId AND p.endStation.stationId = :destinationId")
   Optional<PathEntity> findByStationIds(Long sourceId, Long destinationId);

   @Query("SELECT p FROM PathEntity p WHERE p.startStation.stationId = :sourceId")
   List<PathEntity> findByStartStationId(Long sourceId);
}
