package io.agentflow.execution;

import io.agentflow.execution.environment.DefaultExecutionEnvironment;
import io.agentflow.execution.interceptor.ExecutionInterceptorChain;
import io.agentflow.execution.lifecycle.NoopExecutionLifecycle;
import io.agentflow.execution.pipeline.DefaultExecutionPipeline;
import io.agentflow.execution.pipeline.ToolExecutionStep;
import io.agentflow.execution.result.DefaultExecutionResultHandler;
import io.agentflow.tool.DefaultToolRegistry;
import io.agentflow.tool.ToolBinding;
import io.agentflow.tool.ToolDefinition;
import io.agentflow.tool.ToolInvoker;
import io.agentflow.tool.ToolRegistry;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultExecutionEngineToolRegistryTest {

    @Test
    void shouldExecuteRegisteredTool() {

        ToolDefinition definition =
                new ToolDefinition(
                        "order-query",
                        "query order");

        ToolInvoker invoker =
                (tool, arguments) -> "order-result";
        ToolRegistry registry = new DefaultToolRegistry();

        registry.register(
                new ToolBinding(
                        definition,
                        invoker));

        ToolBinding binding = registry.get("order-query");

        ToolExecutionStep step =
                new ToolExecutionStep(
                        binding.definition(),
                        binding.invoker());

        DefaultExecutionPipeline pipeline = new DefaultExecutionPipeline(List.of(step));

        DefaultExecutionEngine engine =
                new DefaultExecutionEngine(
                        new DefaultExecutionEnvironment(
                                new DefaultExecutionContextFactory(),
                                new NoopExecutionLifecycle(),
                                new ExecutionInterceptorChain(List.of()),
                                pipeline,
                                new DefaultExecutionResultHandler()));

        Execution execution =
                new Execution(
                        "execution-001",
                        new ExecutionDefinition(
                                "assistant",
                                "system",
                                "query order"));

        ExecutionResult result = engine.execute(execution);

        assertEquals(
                "order-result",
                result.content());
    }

}