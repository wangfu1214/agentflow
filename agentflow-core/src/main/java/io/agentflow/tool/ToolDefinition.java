package io.agentflow.tool;

import java.util.Objects;


/**
 * Definition of a tool capability.
 *
 * Core model does not know how the tool is implemented.
 */
public final class ToolDefinition {


    private final String name;


    private final String description;



    public ToolDefinition(
            String name,
            String description
    ) {

        this.name =
                requireNotBlank(
                        name,
                        "name"
                );


        this.description =
                description == null
                        ? ""
                        : description;

    }



    public String name() {

        return name;

    }



    public String description() {

        return description;

    }



    private static String requireNotBlank(
            String value,
            String fieldName
    ) {

        Objects.requireNonNull(
                value,
                fieldName + " must not be null"
        );


        if(value.isBlank()) {

            throw new IllegalArgumentException(
                    fieldName + " must not be blank"
            );

        }


        return value;

    }

}