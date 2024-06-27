package com.ingenia.travel.application.ports.input;

import java.util.List;

import com.ingenia.travel.domain.model.Path;
import com.ingenia.travel.infrastructure.adapters.input.rest.model.response.ShortPathResponse;

public interface PathServicePort {

   Path findById(Long id);

   List<Path> findAll();

   Path save(Path path);

   Path update(Long id, Path path);

   void deleteById(Long id);

   ShortPathResponse findShortPathById(Long sourceId, Long destinationId);
}
