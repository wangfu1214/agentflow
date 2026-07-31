package io.agentflow.agent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as an agent definition source.
 *
 * The annotated class can be discovered and converted
 * into an Agent definition.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AgentDefinition {

    /**
     * agent name
     */
    String name();

    /**
     * prompt
     */
    String systemPrompt();

    /**
     * tools
     */
    String [] tools() default {};
}
