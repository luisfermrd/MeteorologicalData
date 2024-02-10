package com.api.MeteorologicalData.util;

public enum ApiType {
    CURRENT_WEATHER("Current Weather"),
    FORECAST("Forecast"),
    AIR_POLLUTION("Air Pollution");

    private final String displayName;

    ApiType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
