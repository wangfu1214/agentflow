package io.agentflow.extension;

import io.agentflow.autoconfigure.extension.ExtensionRegistrar;
import io.agentflow.autoconfigure.extension.ExtensionScanner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtensionRegistryTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner().withUserConfiguration(
                    TestConfiguration.class, ExtensionTestConfiguration.class);


    @Test
    void shouldRegisterExtension() {

        contextRunner.run(context -> {
            ExtensionScanner scanner =
                    context.getBean(ExtensionScanner.class);

            ExtensionRegistry registry =
                    context.getBean(ExtensionRegistry.class);

            System.out.println(
                    "extensions = " + scanner.scan()
            );

            assertThat(registry.contains("mock-extension"))
                    .isTrue();

            assertThat(registry.get("mock-extension"))
                    .isInstanceOf(MockExtension.class);
        });
    }


    @Configuration
    static class TestConfiguration {

        @Bean
        MockExtension mockExtension() {
            return new MockExtension();
        }
    }

    @Configuration
    static class ExtensionTestConfiguration {

        @Bean
        ExtensionRegistry extensionRegistry() {
            return new ExtensionRegistry();
        }

        @Bean
        ExtensionScanner extensionScanner(
                ApplicationContext context) {

            return new ExtensionScanner(context);
        }

        @Bean
        ExtensionRegistrar extensionRegistrar(
                ExtensionScanner scanner,
                ExtensionRegistry registry) {

            return new ExtensionRegistrar(
                    scanner, registry
            );
        }
    }


    @AgentFlowExtension(
            name = "mock-extension",
            description = "test extension"
    )
    static class MockExtension {

    }
}


