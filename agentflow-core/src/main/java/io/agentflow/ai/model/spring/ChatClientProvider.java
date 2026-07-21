package io.agentflow.ai.model.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatClientProvider {

    private final ChatClient chatClient;

    public ChatClient get(String model) {
        return chatClient;
    }
}
