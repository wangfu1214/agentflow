package io.agentflow.agent;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AgentRequestTest {


    @Test
    void shouldCreateAgentRequest() {

        AgentRequest request = new AgentRequest("query order 123");
        assertEquals("query order 123", request.input());
    }

    @Test
    void shouldRejectBlankInput() {
        assertThrows(IllegalArgumentException.class, () -> new AgentRequest(""));
    }

}