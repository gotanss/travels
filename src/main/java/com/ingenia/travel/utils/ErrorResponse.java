package com.ingenia.travel.utils;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorResponse {

   private String path;

   private int status;

   private String message;

   private String code;

   private List<String> details;

   private LocalDateTime timestamp;
}
