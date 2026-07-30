package io.agentflow.tool;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultToolRegistryTest {

    @Test
    void shouldRegisterAndGetTool() {

        ToolDefinition definition = new ToolDefinition("order-query", "query order");

        ToolInvoker invoker = (tool, arguments) -> "result";

        ToolBinding binding = new ToolBinding(definition, invoker);

        ToolRegistry registry = new DefaultToolRegistry();

        registry.register(binding);

        ToolBinding result =
                registry.get("order-query");

        assertEquals(
                "order-query",
                result.definition().name());

        assertEquals(invoker, result.invoker());
    }

    @Test
    void shouldRejectUnknownTool() {
        ToolRegistry registry =
                new DefaultToolRegistry();
        assertThrows(
                IllegalArgumentException.class,
                () -> registry.get("unknown"));
    }

}