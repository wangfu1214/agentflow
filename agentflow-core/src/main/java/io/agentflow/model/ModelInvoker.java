package io.agentflow.model;

/**
 * Port used by the execution kernel to invoke an AI model.
 */
@FunctionalInterface
public interface ModelInvoker {

    ModelResponse invoke(ModelRequest request);
}