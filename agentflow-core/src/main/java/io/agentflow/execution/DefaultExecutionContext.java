package io.agentflow.execution;

import io.agentflow.execution.record.ExecutionRecord;

import java.util.HashMap;
import java.util.Map;

public class DefaultExecutionContext implements ExecutionContext {

    private final Execution execution;


    private ExecutionStatus state;

    private final ExecutionRecord record;

    private final Map<String,Object> attributes;

    public DefaultExecutionContext(Execution execution) {
        this.execution = execution;
        this.state = ExecutionStatus.CREATED;
        this.attributes = new HashMap<>();
        this.record = new ExecutionRecord(execution.id());
    }

    @Override
    public Execution getExecution() {
        return execution;
    }

    @Override
    public ExecutionStatus getStatus() {
        return state;
    }

    @Override
    public void setStatus(ExecutionStatus state) {
        this.state = state;
    }

    @Override
    public ExecutionRecord record() {
        return record;
    }

    @Override
    public Object get(String key) {
        return attributes.get(key);
    }

    @Override
    public void put(String key, Object value) {
        attributes.put(key, value);
    }
}
