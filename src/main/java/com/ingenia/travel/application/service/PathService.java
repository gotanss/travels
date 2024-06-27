package com.ingenia.travel.application.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.stereotype.Service;

import com.ingenia.travel.application.ports.input.PathServicePort;
import com.ingenia.travel.application.ports.output.PathPersistencePort;
import com.ingenia.travel.domain.exception.PathDuplicateException;
import com.ingenia.travel.domain.exception.PathNotFoundException;
import com.ingenia.travel.domain.model.Path;
import com.ingenia.travel.infrastructure.adapters.input.rest.model.response.ShortPathResponse;
import com.ingenia.travel.infrastructure.adapters.output.persistence.mapper.PathPersistenceMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PathService implements PathServicePort {

   private final PathPersistencePort pathPersistencePort;

   private final PathPersistenceMapper pathPersistenceMapper;

   @Override
   public Path findById(Long id) {
      return pathPersistencePort.findById(id).orElseThrow(PathNotFoundException::new);
   }

   @Override
   public List<Path> findAll() {
      return pathPersistencePort.findAll();
   }

   @Override
   public Path save(Path path) {
      if (!pathPersistencePort.findByStationIds(path.getSourceId(), path.getDestinationId()).isEmpty()) {
         throw new PathDuplicateException();
      }
      Path pathReverse = Path.builder().cost(path.getCost()).sourceId(path.getDestinationId()).destinationId(path.getSourceId()).build();
      pathPersistencePort.save(pathReverse);
      return pathPersistencePort.save(path);
   }

   @Override
   public Path update(Long id, Path path) {
      return pathPersistencePort.findById(id).map(savedRoad -> {
         savedRoad.setCost(path.getCost());
         savedRoad.setSourceId(path.getSourceId());
         savedRoad.setDestinationId(path.getDestinationId());
         return pathPersistencePort.save(savedRoad);
      }).orElseThrow(PathNotFoundException::new);
   }

   @Override
   public void deleteById(Long id) {
      if (pathPersistencePort.findById(id).isEmpty()) {
         throw new PathNotFoundException();
      }
      pathPersistencePort.deleteById(id);
   }

   @Override
   public ShortPathResponse findShortPathById(Long sourceId, Long destinationId) {
      ShortPathResponse paths = findShortestPath(sourceId, destinationId);
      //      ShortPathResponse shortPathResponse = ShortPathResponse.builder().paths(paths).cost(0.0).build();
      return paths;
   }

   public ShortPathResponse findShortestPath(Long sourceId, Long destinationId) {
      Map<Long, Double> distances = new HashMap<>();
      Map<Long, Long> previous = new HashMap<>();
      PriorityQueue<Long> nodes = new PriorityQueue<>(Comparator.comparing(distances::get));

      // Inicialización
      distances.put(sourceId, 0.0);
      nodes.add(sourceId);

      while (!nodes.isEmpty()) {
         Long current = nodes.poll();

         if (current.equals(destinationId)) {
            return reconstructPath(previous, sourceId, destinationId);
         }

         List<Path> caminos = pathPersistencePort.findBySourceId(current);
         for (Path camino : caminos) {
            Long neighbor = camino.getDestinationId();
            Double newDist = distances.get(current) + camino.getCost();

            if (newDist < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
               distances.put(neighbor, newDist);
               previous.put(neighbor, current);
               nodes.add(neighbor);
            }
         }
      }

      //      return Collections.emptyList(); // Si no se encuentra un camino
      return ShortPathResponse.builder().build(); // Si no se encuentra un camino
   }

   private ShortPathResponse reconstructPath(Map<Long, Long> previous, Long sourceId, Long destinationId) {
      List<Path> paths = new ArrayList<>();
      List<Long> pathIds = new ArrayList<>();
      pathIds.add(destinationId);
      Double costoTotal = 0.0;
      for (Long at = destinationId; at != null; at = previous.get(at)) {
         Long prev = previous.get(at);
         if (prev != null) {
            Path path = pathPersistencePort.findByStationIds(prev, at).get();
            paths.add(path);
            pathIds.add(prev);
            costoTotal += path.getCost();
         }
      }

      Collections.reverse(paths);
      Collections.reverse(pathIds);
      //      return paths;

      ShortPathResponse shortPathResponse = ShortPathResponse.builder().pathIds(pathIds).cost(costoTotal).build();
      return shortPathResponse;

   }

}
