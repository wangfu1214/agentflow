package io.agentflow.tool;

import io.agentflow.ai.tool.annotation.AgentTool;
import org.springframework.ai.tool.annotation.Tool;

import java.time.LocalDateTime;


@AgentTool(name = "time", description = "获取系统当前时间", category = "system")
public class TimeTool {

    @Tool(description = "获取当前时间，当用户需要获取当天几点几分时调用")
    public String currentTime(){
        return LocalDateTime.now().toString();
    }
}
