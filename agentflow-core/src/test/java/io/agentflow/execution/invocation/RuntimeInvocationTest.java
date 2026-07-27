package io.agentflow.execution.invocation;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;


class RuntimeInvocationTest {


    @Test
    void shouldDefineInvocationType() {

        assertEquals(
                InvocationType.MODEL,
                InvocationType.valueOf("MODEL")
        );


        assertEquals(
                InvocationType.TOOL,
                InvocationType.valueOf("TOOL")
        );

    }



    @Test
    void shouldDefineInvocationLifecycleStatus() {

        assertEquals(
                RuntimeInvocationStatus.CREATED,
                RuntimeInvocationStatus.valueOf(
                        "CREATED"
                )
        );


        assertEquals(
                RuntimeInvocationStatus.RUNNING,
                RuntimeInvocationStatus.valueOf(
                        "RUNNING"
                )
        );


        assertEquals(
                RuntimeInvocationStatus.SUCCEEDED,
                RuntimeInvocationStatus.valueOf(
                        "SUCCEEDED"
                )
        );


        assertEquals(
                RuntimeInvocationStatus.FAILED,
                RuntimeInvocationStatus.valueOf(
                        "FAILED"
                )
        );

    }



    @Test
    void shouldAllowInvocationImplementation() {


        RuntimeInvocation invocation =
                new RuntimeInvocation() {


                    @Override
                    public String id() {
                        return "invocation-001";
                    }


                    @Override
                    public String executionId() {
                        return "execution-001";
                    }


                    @Override
                    public InvocationType type() {
                        return InvocationType.MODEL;
                    }


                    @Override
                    public RuntimeInvocationStatus status() {
                        return RuntimeInvocationStatus.CREATED;
                    }

                    @Override
                    public void start() {

                    }

                    @Override
                    public void succeed(Object result) {

                    }

                    @Override
                    public void fail(Exception exception) {

                    }
                };


        assertEquals(
                "invocation-001",
                invocation.id()
        );


        assertEquals(
                "execution-001",
                invocation.executionId()
        );


        assertEquals(
                InvocationType.MODEL,
                invocation.type()
        );


        assertEquals(
                RuntimeInvocationStatus.CREATED,
                invocation.status()
        );

    }

}