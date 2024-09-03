package io.dataSynthAI.dataSynthAI_backend.config;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class SchemaConfig {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String SCHEMA_VALIDATION_FILE = "fieldRequirementValidation.json";
    private static final String DATA_SYNTH_SCHEMA = "schema/DataSynthSchema.json";
    private static final String DATA_SYNTH_INPUT = "schema/DataSynthInput.json";
    public JsonNode getJsonSchemaNode() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream( SCHEMA_VALIDATION_FILE ) ;
        return objectMapper.readTree(is);
    }

    public JsonNode getJsonNodeDataSynthSchema() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream( DATA_SYNTH_SCHEMA ) ;
        return objectMapper.readTree(is);
    }

    public JsonNode getJsonNodeDataSynthInput() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream( DATA_SYNTH_INPUT ) ;
        return objectMapper.readTree(is);
    }
    public JsonSchema getDataSynthSchema(){
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        try {
            return factory.getSchema(getJsonNodeDataSynthSchema());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
