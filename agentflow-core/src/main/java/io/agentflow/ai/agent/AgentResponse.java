package io.agentflow.ai.agent;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentResponse {

    /**
     * Agent 最终回答
     */
    private String answer;

    /**
     * 使用的模型
     */
    private String model;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;
}