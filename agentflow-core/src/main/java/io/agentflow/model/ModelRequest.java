package io.agentflow.model;

import java.util.Objects;

/**
 * Request sent by the execution kernel to a model provider.
 *
 * @param systemPrompt system instructions
 * @param input        user input
 */
public record ModelRequest( String systemPrompt,
                            String input) {

    public ModelRequest {
        systemPrompt = requireNotBlank(systemPrompt, "systemPrompt");
        input = requireNotBlank(input, "input");
    }

    private static String requireNotBlank(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " must not be blank");
        }
        return value;
    }
}
