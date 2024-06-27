package com.ingenia.travel.application.ports.input;

import java.util.List;

import com.ingenia.travel.domain.model.Station;

public interface StationServicePort {

   Station findById(Long id);

   List<Station> findAll();

   Station save(Station station);

   Station update(Long id, Station station);

   void deleteById(Long id);
}
