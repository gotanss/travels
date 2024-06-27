package com.ingenia.travel.infrastructure.adapters.input.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingenia.travel.application.ports.input.PathServicePort;
import com.ingenia.travel.infrastructure.adapters.input.rest.mapper.PathRestMapper;
import com.ingenia.travel.infrastructure.adapters.input.rest.model.request.PathRequest;
import com.ingenia.travel.infrastructure.adapters.input.rest.model.response.PathResponse;
import com.ingenia.travel.infrastructure.adapters.input.rest.model.response.ShortPathResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/paths")
public class PathController {

   private final PathServicePort pathService;

   private final PathRestMapper pathRestMapper;

   @GetMapping
   public List<PathResponse> findAll() {
      return pathRestMapper.toPathResponses(pathService.findAll());
   }

   @GetMapping("{id}")
   public PathResponse findById(@PathVariable Long id) {
      return pathRestMapper.toPathResponse(pathService.findById(id));
   }

   @PostMapping
   public ResponseEntity<PathResponse> save(@RequestBody @Valid PathRequest roadRequest) {
      return ResponseEntity.status(HttpStatus.CREATED).body(pathRestMapper.toPathResponse(pathService.save(pathRestMapper.toPath(roadRequest))));
   }

   @GetMapping("/{sourceId}/{destinationId}")
   public ShortPathResponse findShortPath(@PathVariable Long sourceId, @PathVariable Long destinationId) {
      return pathService.findShortPathById(sourceId, destinationId);
   }

}
