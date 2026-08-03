package io.agentflow.tool;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ToolDefinitionTest {


    @Test
    void shouldCreateToolDefinition() {
        ToolDefinition definition = new ToolDefinition("order-query", "query order information");

        assertEquals(
                "order-query",
                definition.name());

        assertEquals(
                "query order information",
                definition.description());
    }



    @Test
    void shouldRejectBlankName() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new ToolDefinition(
                        "",
                        "description"));

    }

}