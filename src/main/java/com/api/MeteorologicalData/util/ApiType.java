package com.api.MeteorologicalData.util;

/**
 * Enumeration representing the available API types.
 * Each type has a descriptive name associated with it.
 */
public enum ApiType {
    CURRENT_WEATHER("Current Weather"),
    FORECAST("Forecast"),
    AIR_POLLUTION("Air Pollution");

    private final String displayName;

    ApiType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the friendly name of the API type.
     *
     * @return The friendly name of the API type.
     */
    public String getDisplayName() {
        return displayName;
    }
}
