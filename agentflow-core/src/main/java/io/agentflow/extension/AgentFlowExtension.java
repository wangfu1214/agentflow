package io.agentflow.extension;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AgentFlowExtension {

    /**
     * 扩展名称
     */
    String name();

    /**
     * 扩展描述
     */
    String description() default "";
}
