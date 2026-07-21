package io.agentflow.ai.prompt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PromptTemplate {

    /**
     * 模板唯一名称
     */
    private String name;

    /**
     * Prompt 内容
     */
    private String content;

    /**
     * Prompt 描述
     */
    private String description;

    /**
     * 是否启用
     */
    private boolean enabled;

}
