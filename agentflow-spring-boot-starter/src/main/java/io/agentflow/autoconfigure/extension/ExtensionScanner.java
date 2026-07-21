package io.agentflow.autoconfigure.extension;

import io.agentflow.extension.AgentFlowExtension;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.stream.Collectors;

public class ExtensionScanner {

    private final ApplicationContext applicationContext;

    public ExtensionScanner(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Map<String, Object> scan() {

        Map<String,Object> beans =
                applicationContext.getBeansOfType(Object.class);

        return beans.entrySet()
                .stream()
                .filter(entry -> {

                    Class<?> clazz =
                            AopUtils.getTargetClass(entry.getValue());

                    return clazz.isAnnotationPresent(
                            AgentFlowExtension.class
                    );

                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }
}
