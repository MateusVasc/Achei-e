package com.upe.br.acheie.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Major {

  SOFTWARE_ENGINEERING("Software Engineering"),
  COMPUTER_SCIENCE_TEACHING("Computer Science Teaching"),
  MEDICINE("Medicine"),
  PSYCHOLOGY("Psychology"),
  HISTORY_TEACHING("History Teaching"),
  GEOGRAPHY_TEACHING("Geography Teaching"),
  BIOLOGICAL_SCIENCES_TEACHING("Biological Sciences Teaching"),
  LITERATURE_TEACHING("Literature Teaching"),
  PEDAGOGY_TEACHING("Pedagogy Teaching"),
  MATHEMATICS_TEACHING("Mathematics Teaching");

  private final String value;
}
