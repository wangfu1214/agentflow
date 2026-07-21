package io.agentflow.ai.tool;

import lombok.Builder;
import lombok.Data;

/**
 * 描述一个可被 agentFlow 管理和调用的 tool 定义
 */
@Data
@Builder
public class ToolDefinition {

    /**
     * 元数据
     */
    private ToolMetadata toolMetadata;

    /**
     * Spring Bean 实例
     */
    private Object bean;

    /**
     * Bean 类型
     */
    private Class<?> beanClass;
}
