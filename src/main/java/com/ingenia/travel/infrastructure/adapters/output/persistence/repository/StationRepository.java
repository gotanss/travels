package com.ingenia.travel.infrastructure.adapters.output.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingenia.travel.infrastructure.adapters.output.persistence.entity.StationEntity;

public interface StationRepository extends JpaRepository<StationEntity, Long> {

   Optional<StationEntity> findByName(String name);
}
