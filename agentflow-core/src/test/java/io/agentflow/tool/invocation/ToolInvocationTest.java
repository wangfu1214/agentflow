package io.agentflow.tool.invocation;

import io.agentflow.execution.invocation.RuntimeInvocation;
import io.agentflow.execution.invocation.RuntimeInvocationStatus;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ToolInvocationTest {


    @Test
    void shouldCreateToolInvocation() {

        ToolInvocation invocation =
                new ToolInvocation(
                        "invocation-001",
                        "execution-001",
                        "order-query",
                        Map.of(
                                "id",
                                "123"
                        )
                );


        assertEquals(
                "order-query",
                invocation.toolName()
        );


        assertEquals(
                RuntimeInvocationStatus.CREATED,
                invocation.status()
        );

    }



    @Test
    void shouldSupportRuntimeInvocationLifecycle() {


        ToolInvocation invocation =
                new ToolInvocation(
                        "invocation-001",
                        "execution-001",
                        "order-query",
                        Map.of(
                                "id",
                                "123"
                        )
                );


        RuntimeInvocation runtimeInvocation =
                invocation;


        runtimeInvocation.start();


        assertEquals(
                RuntimeInvocationStatus.RUNNING,
                runtimeInvocation.status()
        );


        RuntimeException exception =
                new RuntimeException(
                        "tool failed"
                );


        runtimeInvocation.fail(
                exception
        );


        assertEquals(
                RuntimeInvocationStatus.FAILED,
                invocation.status()
        );


        assertEquals(
                exception,
                invocation.failure()
        );

    }

}