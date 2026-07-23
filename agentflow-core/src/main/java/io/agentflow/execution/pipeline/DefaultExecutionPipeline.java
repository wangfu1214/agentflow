package io.agentflow.execution.pipeline;

import io.agentflow.execution.ExecutionContext;

import java.util.List;

public class DefaultExecutionPipeline implements ExecutionPipeline {

    private final List<ExecutionStep> steps;

    public DefaultExecutionPipeline(List<ExecutionStep> steps){
        this.steps = List.copyOf(steps);
    }

    @Override
    public void execute(ExecutionContext context) {
        for (ExecutionStep step : steps) {
            step.execute(context);
        }
    }
}
