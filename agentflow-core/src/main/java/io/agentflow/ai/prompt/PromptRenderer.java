package io.agentflow.ai.prompt;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PromptRenderer {


    public String render(PromptContext context) {
        return render(context.getPromptTemplate(), context.getPromptVariables());
    }

    public String render(PromptTemplate template, PromptVariables variables) {
        String content = template.getContent();

        if (variables == null || variables.isEmpty()) {
            return content;
        }

        for (Map.Entry<String, Object> entry : variables.asMap().entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";
            String value = entry.getValue() == null ? "" : entry.getValue().toString();
            content = content.replace(placeholder, value);
        }

        return content;
    }
}
