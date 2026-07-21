package io.agentflow.ai.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ModelOptions {

    /**
     * Provider 名称
     * 例如:
     * spring-ai
     * openai
     * deepseek
     */
    private String provider;

    /**
     * 模型名称
     */
    private String model;

    /**
     * 温度，控制随机性
     */
    private Double temperature;

    /**
     * 最大输出 token 数
     */
    private Integer maxTokens;

    /**
     * 是否流式输出
     */
    @Builder.Default
    private boolean stream = false;
}
