package io.agentflow.execution.result;

import io.agentflow.execution.ExecutionContext;
import io.agentflow.execution.ExecutionContextKeys;
import io.agentflow.execution.ExecutionResult;
import io.agentflow.model.ModelResponse;

public class DefaultExecutionResultHandler implements ExecutionResultHandler {
    @Override
    public ExecutionResult handler(ExecutionContext context) {
        ModelResponse response =
                (ModelResponse)
                        context.get(ExecutionContextKeys.MODEL_RESPONSE);


        if(response == null){
            throw new IllegalStateException(
                    "modelResponse not found"
            );
        }


        return ExecutionResult.of(
                response.content()
        );
    }
}
