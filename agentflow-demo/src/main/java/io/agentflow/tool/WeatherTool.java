package io.agentflow.tool;


import io.agentflow.ai.tool.annotation.AgentTool;
import io.agentflow.record.WeatherRecord;
import org.springframework.ai.tool.annotation.Tool;

@AgentTool(name = "weather", description = "获取指定城市天气", category = "system")
public class WeatherTool{

    @Tool(description = "获取指定城市的天气，当用户需要询问某个城市天气，气温，是否下雨时调用")
    public WeatherRecord weather(String city){

        return new WeatherRecord("shenzhen", 32, "晴");

    }
}
