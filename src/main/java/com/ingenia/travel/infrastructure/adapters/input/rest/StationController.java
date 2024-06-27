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

import com.ingenia.travel.application.ports.input.StationServicePort;
import com.ingenia.travel.infrastructure.adapters.input.rest.mapper.StationRestMapper;
import com.ingenia.travel.infrastructure.adapters.input.rest.model.request.StationRequest;
import com.ingenia.travel.infrastructure.adapters.input.rest.model.response.StationResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/stations")
public class StationController {

   private final StationServicePort stationService;

   private final StationRestMapper stationRestMapper;

   @GetMapping
   public List<StationResponse> findAll() {
      return stationRestMapper.toStationResponses(stationService.findAll());
   }

   @GetMapping("/v1/{id}")
   public StationResponse findById(@PathVariable Long id) {
      return stationRestMapper.toStationResponse(stationService.findById(id));
   }

   @PostMapping
   public ResponseEntity<StationResponse> save(@RequestBody @Valid StationRequest stationRequest) {
      return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(stationRestMapper.toStationResponse(stationService.save(stationRestMapper.toStation(stationRequest))));
   }
}
