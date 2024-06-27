package com.ingenia.travel.utils;

import lombok.Getter;

@Getter
public enum ErrorCatalog {
   PATH_NOT_FOUND("ERR_Cuenta_001", "Camino no encontrado."),
   STATION_DUPLICATE("ERR_Cuenta_002", "La estación ya existe."),
   PATH_DUPLICATE("ERR_Cuenta_003", "El camino ya existe."),
   INVALID_PATH("ERR_Cuenta_004","Parametros de camino invalidos."),
   GENERIC_ERROR("ERR_Cuenta_005", "Error insesperado.");

   private final String code;

   private final String message;

   ErrorCatalog(String code, String message) {
      this.code = code;
      this.message = message;
   }
}
