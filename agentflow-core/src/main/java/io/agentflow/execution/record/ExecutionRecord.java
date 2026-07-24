package io.agentflow.execution.record;

import io.agentflow.model.invocation.ModelInvocation;

import java.time.Instant;
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


    private ExecutionRecordStatus status;


    private Instant startedAt;


    private Instant completedAt;

    public ExecutionRecord(String executionId) {
        this.executionId = Objects.requireNonNull(executionId, "executionId must not be null");
        this.modelInvocations = new ArrayList<>();
        this.status = ExecutionRecordStatus.CREATED;
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

    public ExecutionRecordStatus status(){

        return status;

    }



    public Instant startedAt(){

        return startedAt;

    }



    public Instant completedAt(){

        return completedAt;

    }

    public void start(){

        requireStatus(
                ExecutionRecordStatus.CREATED
        );


        status =
                ExecutionRecordStatus.RUNNING;


        startedAt =
                Instant.now();

    }



    public void complete(){

        requireStatus(
                ExecutionRecordStatus.RUNNING
        );


        status =
                ExecutionRecordStatus.COMPLETED;


        completedAt =
                Instant.now();

    }



    public void fail(){

        requireStatus(
                ExecutionRecordStatus.RUNNING
        );


        status =
                ExecutionRecordStatus.FAILED;


        completedAt =
                Instant.now();

    }


    private void requireStatus(
            ExecutionRecordStatus expected
    ){

        if(status != expected){

            throw new IllegalStateException(
                    "expected execution record status "
                            + expected
                            + " but was "
                            + status
            );

        }

    }
}
