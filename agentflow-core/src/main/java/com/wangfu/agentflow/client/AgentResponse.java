package com.wangfu.agentflow.client;

import java.util.Objects;

public record AgentResponse(String executionId, String content) {

    public AgentResponse {
        Objects.requireNonNull(executionId, "executionId must not be null");
        Objects.requireNonNull(content, "content must not be null");

        if (executionId.isBlank()) {
            throw new IllegalArgumentException(
                    "executionId must not be blank"
            );
        }
    }

    public static AgentResponse of(String executionId, String content) {
        return new AgentResponse(executionId, content);
    }

}
