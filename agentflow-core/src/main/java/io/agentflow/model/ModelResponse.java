package io.agentflow.model;

import java.util.Objects;

/**
 * Response returned by a model provider.
 *
 * @param content textual model output
 */
public record ModelResponse(String content) {

    public ModelResponse {
        Objects.requireNonNull(
                content,
                "content must not be null"
        );
    }

    public static ModelResponse of(String content) {
        return new ModelResponse(content);
    }
}
