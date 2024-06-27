package com.ingenia.travel.infrastructure.adapters.input.rest.model.response;

import java.util.List;

import com.ingenia.travel.domain.model.Path;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortPathResponse {

   private List<Path> paths;

   private List<Long> pathIds;

   private double cost;
}
