package io.agentflow.execution.record;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


class ExecutionRecordTest {


    @Test
    void shouldCreateRecordInCreatedStatus() {


        ExecutionRecord record =
                new ExecutionRecord(
                        "execution-001"
                );


        assertEquals(
                ExecutionRecordStatus.CREATED,
                record.status()
        );


        assertNull(
                record.startedAt()
        );


        assertNull(
                record.completedAt()
        );

    }



    @Test
    void shouldCompleteLifecycle() {


        ExecutionRecord record =
                new ExecutionRecord(
                        "execution-001"
                );


        record.start();


        record.complete();



        assertEquals(
                ExecutionRecordStatus.COMPLETED,
                record.status()
        );


        assertNotNull(
                record.startedAt()
        );


        assertNotNull(
                record.completedAt()
        );

    }



    @Test
    void shouldFailLifecycle() {


        ExecutionRecord record =
                new ExecutionRecord(
                        "execution-001"
                );


        record.start();


        record.fail();



        assertEquals(
                ExecutionRecordStatus.FAILED,
                record.status()
        );


        assertNotNull(
                record.completedAt()
        );

    }



    @Test
    void shouldRejectCompleteBeforeStart() {


        ExecutionRecord record =
                new ExecutionRecord(
                        "execution-001"
                );


        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        record::complete
                );


        assertEquals(
                "expected execution record status RUNNING but was CREATED",
                exception.getMessage()
        );

    }

}