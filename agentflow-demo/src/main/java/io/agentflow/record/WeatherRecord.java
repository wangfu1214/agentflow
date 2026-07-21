package io.agentflow.record;

public record WeatherRecord(
        String city,
        Integer temperature,
        String weather
) {
}
