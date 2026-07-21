package io.agentflow.ai.prompt;

import io.agentflow.ai.prompt.constant.PromptConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PromptLoader {

    private final ResourcePatternResolver resourcePatternResolver;

    public List<PromptTemplate> load() {
        try {
            Resource[] resources = resourcePatternResolver.getResources(PromptConstants.SYSTEM_RESOURCE_PATH);
            return Arrays.stream(resources)
                    .map(this::toTemplate)
                    .toList();
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to load prompt resources.", e);
        }
    }

    private PromptTemplate toTemplate(Resource resource) {
        String filename = resource.getFilename();
        if (filename == null || !filename.endsWith(".md")) {
            throw new IllegalStateException("Invalid prompt file: " + filename);
        }
        try {
            String name = filename.substring(0, filename.length() - 3);
            String content = StreamUtils.copyToString(
                    resource.getInputStream(),
                    StandardCharsets.UTF_8
            );
            return PromptTemplate.builder()
                    .content(content)
                    .name(name)
                    .description("Prompt loaded from " + filename)
                    .enabled(true)
                    .build();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load prompt template: " + resource, e);
        }
    }
}
