package io.agentflow.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "agentflow")
public class AgentFlowProperties {

    /**
     * 默认模型名称
     */
    private String defaultModel = "deepseek-chat";

    /**
     * 是否启用 AgentFlow
     */
    private boolean enabled = true;
}
