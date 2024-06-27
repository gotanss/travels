package com.ingenia.travel.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.NotNull;
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
public class PathRequest {

   @NotNull(message = "Field cost cannot be empty or null.")
   private Double cost;

   @NotNull(message = "Field start station cannot be empty or null.")
   private Long sourceId;

   @NotNull(message = "Field end station cannot be empty or null.")
   private Long destinationId;
}
