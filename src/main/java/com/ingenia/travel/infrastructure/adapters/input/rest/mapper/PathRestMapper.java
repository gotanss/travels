package com.ingenia.travel.infrastructure.adapters.input.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;

import com.ingenia.travel.domain.model.Path;
import com.ingenia.travel.infrastructure.adapters.input.rest.model.request.PathRequest;
import com.ingenia.travel.infrastructure.adapters.input.rest.model.response.PathResponse;

@Mapper(componentModel = "spring", mappingInheritanceStrategy = MappingInheritanceStrategy.EXPLICIT)
public interface PathRestMapper {

   Path toPath(PathRequest pathRequest);

   PathResponse toPathResponse(Path path);

   List<PathResponse> toPathResponses(List<Path> paths);
}
