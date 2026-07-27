package io.agentflow.execution.record;


import io.agentflow.execution.invocation.RuntimeInvocation;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public final class ExecutionRecord {


    private final String executionId;


    private final Instant createdAt;


    private final List<RuntimeInvocation> invocations;



    public ExecutionRecord(
            String executionId
    ) {

        this.executionId =
                Objects.requireNonNull(
                        executionId,
                        "executionId must not be null"
                );


        this.createdAt =
                Instant.now();


        this.invocations =
                new ArrayList<>();

    }



    public String executionId() {

        return executionId;

    }



    public Instant createdAt() {

        return createdAt;

    }



    public void addInvocation(
            RuntimeInvocation invocation
    ) {

        invocations.add(
                Objects.requireNonNull(
                        invocation,
                        "invocation must not be null"
                )
        );

    }



    public List<RuntimeInvocation> invocations() {

        return List.copyOf(invocations);
    }



    public RuntimeInvocation lastInvocation() {

        if(invocations.isEmpty()) {

            throw new IllegalStateException(
                    "no invocation found"
            );

        }

        return invocations.get(
                invocations.size() - 1
        );

    }

}