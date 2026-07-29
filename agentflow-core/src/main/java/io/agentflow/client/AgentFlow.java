package io.agentflow.client;

public interface AgentFlow {

    AgentResponse execute(String agentName, AgentRequest request);
}
