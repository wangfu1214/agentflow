package com.wangfu.agentflow.client;

import java.util.Objects;

/**
 * Describes the input of a single agent invocation.
 *
 * @param input textual input supplied by the caller
 */
public record AgentRequest (String input){

    public AgentRequest {
        Objects.requireNonNull(input, "input must not be null");
        if (input.isBlank()) {
            throw new IllegalArgumentException(
                    "input must not be blank"
            );
        }
    }

    public static AgentRequest of(String input) {
        return new AgentRequest(input);
    }
}
