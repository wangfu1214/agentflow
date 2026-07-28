package io.agentflow.agent;

import java.util.Objects;

public record AgentRequest(String input) {

    public AgentRequest {
        Objects.requireNonNull(input, "input is not be null");
        if(input.isBlank()) {
            throw new IllegalArgumentException("input must not be blank");
        }
    }
}
