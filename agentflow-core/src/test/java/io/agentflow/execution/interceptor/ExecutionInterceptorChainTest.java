package io.agentflow.execution.interceptor;


import io.agentflow.execution.DefaultExecutionContext;
import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionContext;
import io.agentflow.execution.ExecutionDefinition;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;


class ExecutionInterceptorChainTest {


    private static final ExecutionDefinition DEFINITION =
            new ExecutionDefinition(
                    "assistant",
                    "system",
                    "hello"
            );


    @Test
    void shouldInvokeInterceptorsInOrder() {


        List<String> events =
                new ArrayList<>();


        ExecutionInterceptor first =
                new ExecutionInterceptor() {

                    @Override
                    public void before(
                            Execution execution,
                            ExecutionContext context
                    ) {

                        events.add(
                                "first-before"
                        );

                    }


                    @Override
                    public void after(
                            Execution execution,
                            ExecutionContext context
                    ) {

                        events.add(
                                "first-after"
                        );

                    }

                };


        ExecutionInterceptor second =
                new ExecutionInterceptor() {

                    @Override
                    public void before(
                            Execution execution,
                            ExecutionContext context
                    ) {

                        events.add(
                                "second-before"
                        );

                    }


                    @Override
                    public void after(
                            Execution execution,
                            ExecutionContext context
                    ) {

                        events.add(
                                "second-after"
                        );

                    }

                };


        ExecutionInterceptorChain chain =
                new ExecutionInterceptorChain(
                        List.of(
                                first,
                                second
                        )
                );


        Execution execution =
                new Execution(
                        "execution-001",
                        DEFINITION
                );


        ExecutionContext context =
                new DefaultExecutionContext(
                        execution
                );


        chain.before(
                execution,
                context
        );


        chain.after(
                execution,
                context
        );


        assertEquals(
                List.of(
                        "first-before",
                        "second-before",
                        "first-after",
                        "second-after"
                ),
                events
        );

    }


    @Test
    void shouldInvokeErrorHandler() {


        List<String> events =
                new ArrayList<>();


        ExecutionInterceptor interceptor = new ExecutionInterceptor() {
            @Override
            public void onError(Execution execution, ExecutionContext context, Exception exception) {
                events.add(
                        "error"
                );
            }
        };

        ExecutionInterceptorChain chain =
                new ExecutionInterceptorChain(
                        List.of(
                                interceptor
                        )
                );


        Execution execution =
                new Execution(
                        "execution-001",
                        DEFINITION
                );


        ExecutionContext context =
                new DefaultExecutionContext(
                        execution
                );


        chain.onError(
                execution,
                context,
                new RuntimeException()
        );


        assertEquals(
                List.of("error"),
                events
        );

    }

}