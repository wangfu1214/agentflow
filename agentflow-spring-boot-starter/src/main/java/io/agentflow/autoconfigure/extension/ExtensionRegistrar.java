package io.agentflow.autoconfigure.extension;

import io.agentflow.extension.ExtensionRegistry;
import jakarta.annotation.PostConstruct;

import java.util.Map;


public class ExtensionRegistrar {

    private final ExtensionRegistry extensionRegistry;

    private final ExtensionScanner extensionScanner;

    public ExtensionRegistrar(ExtensionScanner scanner, ExtensionRegistry extensionRegistry) {
        this.extensionRegistry = extensionRegistry;
        this.extensionScanner = scanner;
    }


    @PostConstruct
    public void init() {
        Map<String, Object> extensions = extensionScanner.scan();
        extensions.forEach(
                (beanName, extension) -> {

                    extensionRegistry.register(
                            "mock-extension",
                            extension
                    );
                }
        );
    }
}
