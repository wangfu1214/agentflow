package io.agentflow.ai.prompt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PromptContext {

    /**
     * Prompt 模版
     */
    private PromptTemplate promptTemplate;

    /**
     * Prompt 变量
     */
    private PromptVariables promptVariables;

    /**
     * 用户原始输入
     */
    private String userMessage;
}
