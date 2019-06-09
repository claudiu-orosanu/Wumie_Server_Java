package com.claudiuorosanu.Wumie.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Language
{
    English,
    Spanish,
    French,
    Romanian;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
