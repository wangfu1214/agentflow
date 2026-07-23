package io.agentflow.execution.pipeline;

import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionContext;
import io.agentflow.execution.ExecutionContextKeys;
import io.agentflow.execution.ExecutionDefinition;
import io.agentflow.model.ModelInvoker;
import io.agentflow.model.ModelRequest;
import io.agentflow.model.ModelResponse;

public class ModelExecutionStep implements ExecutionStep {

    private final ModelInvoker modelInvoker;

    public ModelExecutionStep(ModelInvoker modelInvoker) {
        this.modelInvoker = modelInvoker;
    }

    @Override
    public void execute(ExecutionContext context) {
        Execution execution =
                context.getExecution();


        ExecutionDefinition definition =
                execution.definition();


        ModelRequest request =
                new ModelRequest(
                        definition.systemPrompt(),
                        definition.input()
                );
        ModelResponse response = modelInvoker.invoke(request);
        if (response == null) {
            throw new IllegalStateException(
                    "modelInvoker returned null response"
            );
        }
        context.put(ExecutionContextKeys.MODEL_RESPONSE, response);
    }
}
