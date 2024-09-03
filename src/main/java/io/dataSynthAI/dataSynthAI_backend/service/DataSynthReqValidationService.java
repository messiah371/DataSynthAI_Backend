package io.dataSynthAI.dataSynthAI_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.dataSynthAI.dataSynthAI_backend.config.SchemaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
@Slf4j
public class DataSynthReqValidationService {

    /*private static final String SCHEMA_VALIDATION_FILE = "fieldRequirementValidation.json";

    private final SchemaConfig schemaConfig;

    public DataSynthReqValidationService(SchemaConfig schemaConfig){
        this.schemaConfig = schemaConfig;
    }

    public void inputSchemaLevelValidate() throws IOException {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema jsonSchema = factory.getSchema(schemaConfig.getJsonNodeFieldSchemaStream());
        JsonNode jsonNode = schemaConfig.getJsonNodeInputFile();
        Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);
        if(errors.isEmpty()){
            log.info("Field level validation successfully with input file");
        }
        log.info("Errors occurred : {}", errors);
    }

    public Set<ValidationMessage> validateJson(String json) throws IOException {
        JsonNode jsonNode = schemaConfig.getSampleDataInput();
        return schemaConfig.getSampleProjectJsonSchema().validate(jsonNode);
    }*/
}
