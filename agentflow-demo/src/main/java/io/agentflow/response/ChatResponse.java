package io.agentflow.response;

import lombok.Builder;

@Builder
public record ChatResponse(

        String answer,

        String model,

        Long elapsed,

        Integer promptTokens,

        Integer completionTokens

){}
