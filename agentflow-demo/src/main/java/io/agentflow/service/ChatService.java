package io.agentflow.service;

import io.agentflow.response.ChatResponse;

public interface ChatService {

    /**
     *
     *
     * @param message 入口参数
     * @return
     */
    ChatResponse chat(String message);
}
