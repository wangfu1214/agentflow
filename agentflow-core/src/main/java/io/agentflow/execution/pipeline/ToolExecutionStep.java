package io.agentflow.execution.pipeline;


import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionContext;
import io.agentflow.tool.ToolDefinition;
import io.agentflow.tool.ToolInvoker;
import io.agentflow.tool.invocation.ToolInvocation;
import io.agentflow.tool.invocation.ToolInvocationIdGenerator;
import io.agentflow.tool.invocation.UuidToolInvocationIdGenerator;


import java.util.Map;
import java.util.Objects;



public final class ToolExecutionStep implements ExecutionStep {

    private final ToolDefinition toolDefinition;

    private final ToolInvoker toolInvoker;

    private final ToolInvocationIdGenerator idGenerator;

    public ToolExecutionStep(ToolDefinition toolDefinition, ToolInvoker toolInvoker) {
        this(toolDefinition, toolInvoker, new UuidToolInvocationIdGenerator());
    }

    public ToolExecutionStep(ToolDefinition toolDefinition, ToolInvoker toolInvoker,
            ToolInvocationIdGenerator idGenerator) {
        this.toolDefinition =
                Objects.requireNonNull(toolDefinition, "toolDefinition must not be null");

        this.toolInvoker =
                Objects.requireNonNull(toolInvoker, "toolInvoker must not be null");

        this.idGenerator =
                Objects.requireNonNull(idGenerator, "idGenerator must not be null");
    }



    @Override
    public void execute(ExecutionContext context) {
        Execution execution = context.getExecution();

        ToolInvocation invocation =
                new ToolInvocation(idGenerator.generate(), execution.id(), toolDefinition.name(), Map.of());

        context.record()
                .addInvocation(invocation);

        invocation.start();

        try {
            Object result =
                    toolInvoker.invoke(toolDefinition, invocation.arguments());
            invocation.succeed(result);
        } catch(RuntimeException exception) {
            invocation.fail(exception);
            throw exception;
        }
    }
}