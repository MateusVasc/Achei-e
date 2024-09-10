package com.upe.br.acheie.domain.dto;

public record ErrorDto(String error, String cause) {

	  public ErrorDto(Exception e) {
	    this(e.getMessage(), e.getCause() != null ? e.getCause().toString() : "");
	  }
}