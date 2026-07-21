package io.agentflow.service.impl;

import io.agentflow.ai.agent.AgentRequest;
import io.agentflow.ai.agent.AgentResponse;
import io.agentflow.ai.agent.AgentExecutor;
import io.agentflow.response.ChatResponse;
import io.agentflow.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final AgentExecutor agentExecutor;

    @Override
    public ChatResponse chat(String message) {
        AgentRequest request = AgentRequest.builder()
                .userMessage(message)
                .build();
        AgentResponse response = agentExecutor.execute("default-agent", request);
        return ChatResponse.builder()
                .model("")
                .answer(response.getAnswer())
                .elapsed(0L)
                .promptTokens(0)
                .completionTokens(0)
                .build();
    }
}
