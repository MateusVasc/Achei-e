package com.upe.br.auth.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Semester {

    FIRST("1"),
    SECOND("2"),
    THIRD("3"),
    FOURTH("4"),
    FIFTH("5"),
    SIXTH("6"),
    SEVENTH("7"),
    EIGHTH("8"),
    NINTH("9"),
    TENTH("10"),
    UNSPECIFIED("Unspecified");

    private final String value;
}
