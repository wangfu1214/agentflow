package io.agentflow.execution.record;

import io.agentflow.model.invocation.ModelInvocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Runtime record of one execution.
 *
 * Stores runtime facts produced during execution.
 */
public class ExecutionRecord {

    private final String executionId;


    private final List<ModelInvocation> modelInvocations;

    public ExecutionRecord(String executionId) {
        this.executionId = Objects.requireNonNull(executionId, "executionId must not be null");
        this.modelInvocations = new ArrayList<>();
    }

    public String executionId() {
        return executionId;
    }


    public void addModelInvocation(
            ModelInvocation invocation
    ) {

        modelInvocations.add(
                Objects.requireNonNull(
                        invocation,
                        "invocation must not be null"
                )
        );

    }


    public List<ModelInvocation> modelInvocations() {

        return List.copyOf(
                modelInvocations
        );

    }


    public ModelInvocation lastModelInvocation(){

        if(modelInvocations.isEmpty()){
            throw new IllegalStateException(
                    "no model invocation found"
            );
        }

        return modelInvocations.get(
                modelInvocations.size()-1
        );

    }
}
