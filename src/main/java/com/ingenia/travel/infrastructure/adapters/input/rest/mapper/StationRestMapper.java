package com.ingenia.travel.infrastructure.adapters.input.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;

import com.ingenia.travel.domain.model.Station;
import com.ingenia.travel.infrastructure.adapters.input.rest.model.request.StationRequest;
import com.ingenia.travel.infrastructure.adapters.input.rest.model.response.StationResponse;

@Mapper(componentModel = "spring", mappingInheritanceStrategy = MappingInheritanceStrategy.EXPLICIT)
public interface StationRestMapper {

   Station toStation(StationRequest stationRequest);

   StationResponse toStationResponse(Station station);

   List<StationResponse> toStationResponses(List<Station> stations);
}
