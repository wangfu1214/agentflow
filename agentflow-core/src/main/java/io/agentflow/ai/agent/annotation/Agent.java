package io.agentflow.ai.agent.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Agent {

    /**
     * 唯一名称
     */
    String name();

    /**
     * 模型供应商
     */
    String provider() default "";

    /**
     * Agent 描述
     */
    String description() default "";

    /**
     * 系统 Prompt 名称
     */
    String systemPrompt();

    /**
     * 可用 Tool 名称列表
     */
    String[] tools() default {};

    /**
     * 模型名称
     */
    String model() default "";

    /**
     * 是否启用
     */
    boolean enabled() default true;
}
