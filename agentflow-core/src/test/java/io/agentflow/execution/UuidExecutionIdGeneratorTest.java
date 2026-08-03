package io.agentflow.execution;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UuidExecutionIdGeneratorTest {

    @Test
    void shouldGenerateValidUniqueUuidValues() {
        ExecutionIdGenerator generator = new UuidExecutionIdGenerator();

        String first = generator.generate();
        String second = generator.generate();

        assertNotNull(UUID.fromString(first));
        assertNotNull(UUID.fromString(second));
        assertNotEquals(first, second);
    }
}
