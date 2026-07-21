package io.agentflow.ai.agent;

import io.agentflow.ai.prompt.PromptContext;
import lombok.Builder;
import lombok.Data;

/**
 * Represents a request sent to an Agent for execution.
 */
@Data
@Builder
public class AgentRequest {

    /**
     * 用户输入 user input
     */
    private String userMessage;

    /**
     * Prompt 上下文
     */
    private PromptContext promptContext;

    /**
     * 是否流式输出
     */
    @Builder.Default
    private boolean stream = false;

    /**
     * 模型名称
     */
    private String model;
}
