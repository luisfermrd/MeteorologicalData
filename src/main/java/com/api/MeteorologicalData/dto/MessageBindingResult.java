package com.api.MeteorologicalData.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Data Transfer Object for returning messages and errors during request validation.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageBindingResult {

    /**
     * The message to be displayed to the user.
     */
    private String message;

    /**
     * A map of field names and error messages.
     */
    private Map<String, String> errors;

}
