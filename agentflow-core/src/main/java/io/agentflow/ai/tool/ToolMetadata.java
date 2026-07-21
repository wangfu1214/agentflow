package io.agentflow.ai.tool;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToolMetadata {

    /**
     * Tool 唯一名称
     */
    private String name;

    /**
     * Tool 描述
     */
    private String description;

    /**
     * Tool 分类
     */
    private String category;

    /**
     * 是否启用
     */
    private boolean enabled;
}
