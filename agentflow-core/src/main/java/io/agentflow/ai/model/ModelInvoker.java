package io.agentflow.ai.model;

import io.agentflow.ai.agent.Agent;
import io.agentflow.ai.agent.AgentRequest;
import io.agentflow.ai.agent.AgentResponse;

/**
 * Model invocation abstraction.
 *
 * Different implementations may delegate to
 * Spring AI, OpenAI SDK, LangChain4j, etc.
 */
public interface ModelInvoker {

    AgentResponse invoke(
            Agent agent,
            AgentRequest request,
            String systemPrompt
    );
}
