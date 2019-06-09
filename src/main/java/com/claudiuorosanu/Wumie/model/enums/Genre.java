package com.claudiuorosanu.Wumie.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Genre
{
    Action,
    Adventure,
    Comedy,
    Drama,
    Horror,
    ScienceFiction,
    Historical,
    Musical,
    Western;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
