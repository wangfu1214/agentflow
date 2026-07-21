package io.agentflow.ai.context;

import java.util.Locale;

public record AIContext(
        String userId,

        String sessionId,

        String model,

        Locale locale
){
}
