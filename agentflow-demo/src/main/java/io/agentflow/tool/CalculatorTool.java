package io.agentflow.tool;

import io.agentflow.ai.tool.annotation.AgentTool;
import org.springframework.ai.tool.annotation.Tool;

@AgentTool(name = "calculator", description = "计算两数相加结果", category = "system")
public class CalculatorTool {

    @Tool(description = "获取两个数的相加结果,当用户需要做加法计算时调用")
    public int add(int a, int b){
        return a + b;
    }
}
