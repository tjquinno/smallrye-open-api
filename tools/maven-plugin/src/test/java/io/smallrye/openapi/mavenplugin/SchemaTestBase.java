package io.smallrye.openapi.mavenplugin;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import org.eclipse.microprofile.openapi.models.OpenAPI;

import com.soebes.itf.jupiter.maven.MavenExecutionResult;

public class SchemaTestBase {

    /**
     * Helper method to check that yaml and json schemas match against given test conditions, which are given as consumer.
     * 
     * @param result
     * @param schemaConsumer
     */
    protected void testSchema(MavenExecutionResult result, Consumer<OpenAPI> schemaConsumer) throws IOException {
        schemaConsumer.accept(readJson(result));
        schemaConsumer.accept(readYaml(result));
    }

    private OpenAPI readJson(MavenExecutionResult result) throws IOException {
        File openapiFile = new File(result.getMavenProjectResult().getTargetProjectDirectory(),
                "target/generated/openapi.json");

        return TestObjectMapperHolder.json().readValue(openapiFile, OpenAPI.class);
    }

    private OpenAPI readYaml(MavenExecutionResult result) throws IOException {
        File openapiFile = new File(result.getMavenProjectResult().getTargetProjectDirectory(),
                "target/generated/openapi.yaml");

        return TestObjectMapperHolder.yaml().readValue(openapiFile, OpenAPI.class);
    }
}
