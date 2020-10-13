package org.acme.config;

import io.quarkus.jsonb.JsonbConfigCustomizer;

import javax.json.bind.JsonbConfig;

public class JSONConfig implements JsonbConfigCustomizer {
    public void customize(JsonbConfig config) {
       config.withNullValues(false);
    }
}