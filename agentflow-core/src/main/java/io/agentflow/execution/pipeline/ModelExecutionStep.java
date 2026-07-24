package io.agentflow.execution.pipeline;

import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionContext;
import io.agentflow.execution.ExecutionDefinition;
import io.agentflow.model.ModelInvoker;
import io.agentflow.model.ModelRequest;
import io.agentflow.model.ModelResponse;
import io.agentflow.model.invocation.ModelInvocation;
import io.agentflow.model.invocation.ModelInvocationIdGenerator;
import io.agentflow.model.invocation.UuidModelInvocationIdGenerator;

import java.util.Objects;

public class ModelExecutionStep implements ExecutionStep {

    private final ModelInvoker modelInvoker;

    private final ModelInvocationIdGenerator idGenerator;

    public ModelExecutionStep(ModelInvoker modelInvoker) {
        this(modelInvoker, new UuidModelInvocationIdGenerator());
    }

    public ModelExecutionStep(
            ModelInvoker modelInvoker,
            ModelInvocationIdGenerator idGenerator
    ) {
        this.modelInvoker = Objects.requireNonNull(
                modelInvoker,
                "modelInvoker must not be null"
        );
        this.idGenerator = Objects.requireNonNull(
                idGenerator,
                "idGenerator must not be null"
        );
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

        ModelInvocation invocation =
                new ModelInvocation(
                        idGenerator.generate(),
                        execution.id(),
                        request
                );

        /*
         * Put the invocation into the context before starting it.
         * This ensures error handlers and interceptors can still
         * inspect the invocation when model execution fails.
         */
        context.record()
                .addModelInvocation(
                        invocation
                );

        invocation.start();

        try {
            ModelResponse modelResponse =
                    modelInvoker.invoke(request);

            if (modelResponse == null) {
                throw new IllegalStateException(
                        "modelInvoker returned null response"
                );
            }

            invocation.succeed(modelResponse);

        } catch (RuntimeException exception) {
            invocation.fail(exception);
            throw exception;
        }

    }
}
