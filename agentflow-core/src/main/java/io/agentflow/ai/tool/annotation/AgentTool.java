package io.agentflow.ai.tool.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface AgentTool {

    /**
     * Tool 唯一名称
     */
    String name();

    /**
     * Tool 描述
     */
    String description() default "";

    /**
     * Tool 分类
     */
    String category() default "default";

    /**
     * 是否启用
     */
    boolean enabled() default true;
}
