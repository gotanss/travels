package com.ingenia.travel.infrastructure.adapters.output.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.factory.Mappers;

import com.ingenia.travel.domain.model.Station;
import com.ingenia.travel.infrastructure.adapters.output.persistence.entity.StationEntity;

@Mapper(componentModel = "spring", mappingInheritanceStrategy = MappingInheritanceStrategy.EXPLICIT)
public interface StationPersistenceMapper {

   StationPersistenceMapper INSTANCE = Mappers.getMapper(StationPersistenceMapper.class);

   StationEntity toStationEntity(Station road);

   Station toStation(StationEntity stationEntity);

   List<StationEntity> toStationEntities(List<Station> stations);

   List<Station> toStations(List<StationEntity> stationEntities);

   StationEntity toStationEntity(Long id);
}
