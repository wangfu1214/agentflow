package io.agentflow.ai.model;

public interface ModelProvider {

    /**
     * Provider 名称
     */
     String name();

    /**
     * 创建模型调用器
     */
    ModelInvoker createInvoker();
}
