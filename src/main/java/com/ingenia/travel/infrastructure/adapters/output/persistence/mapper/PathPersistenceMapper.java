package com.ingenia.travel.infrastructure.adapters.output.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.ingenia.travel.domain.model.Path;
import com.ingenia.travel.infrastructure.adapters.output.persistence.entity.PathEntity;

@Mapper(componentModel = "spring", mappingInheritanceStrategy = MappingInheritanceStrategy.EXPLICIT)
public interface PathPersistenceMapper {

   PathPersistenceMapper INSTANCE = Mappers.getMapper(PathPersistenceMapper.class);

   @Mappings({ @Mapping(source = "sourceId", target = "startStation.stationId"),
         @Mapping(source = "destinationId", target = "endStation.stationId") })
   PathEntity toPathEntity(Path road);

   @Mappings({ @Mapping(source = "startStation.stationId", target = "sourceId"), @Mapping(source = "endStation.stationId", target = "destinationId"),
         @Mapping(source = "cost", target = "cost") })
   Path toPath(PathEntity pathEntity);

   List<PathEntity> toPathEntities(List<Path> roads);

   List<Path> toPaths(List<PathEntity> roadEntities);

}
