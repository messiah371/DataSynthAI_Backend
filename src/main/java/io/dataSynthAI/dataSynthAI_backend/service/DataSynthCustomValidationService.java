package io.dataSynthAI.dataSynthAI_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.ValidationMessage;
import io.dataSynthAI.dataSynthAI_backend.config.SchemaConfig;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import static io.dataSynthAI.dataSynthAI_backend.utils.DataSynthUtils.*;

@Service
public class DataSynthCustomValidationService {

    private final SchemaConfig schemaConfig;

    public DataSynthCustomValidationService(SchemaConfig schemaConfig){
        this.schemaConfig = schemaConfig;
    }

    public Set<String> validateDataSynthSchema(String json){
        Set<String> errors = new HashSet<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            if(Objects.isNull(jsonNode)){
                throw new JSONException("Unable to read input json file data");
            }
            Set<ValidationMessage> schemaValidationMessages = schemaConfig.getDataSynthSchema().validate(jsonNode);

            // Collect validation errors from the schema validator
            schemaValidationMessages.forEach(v -> {
                errors.add("Schema Validation Error: " + getErrorMessage(v.getMessage()));
            });

            // Additional validation for regex patterns and possible values
            JsonNode columnsNode = jsonNode.path("Columns");
            if (columnsNode.isArray()) {
                for (JsonNode column : columnsNode) {
                    validateColumn(column, errors);
                }
            }

        } catch (Exception e) {
            errors.add("Failed to parse JSON: " + e.getMessage());
        }
        return errors;
    }
    private void validateColumn(JsonNode column, Set<String> errors) {
        String columnName = column.path("column_name").asText();
        String datatype = column.path("datatype").asText();
        JsonNode possibleValuesNode = column.path("possible_values");
        JsonNode conditionsNode = column.path("conditions");

        // Validate possible values
        if (possibleValuesNode.isArray()) {
            Iterator<JsonNode> possibleValues = possibleValuesNode.elements();
            while (possibleValues.hasNext()) {
                JsonNode valueNode = possibleValues.next();
                if (!validateDataType(valueNode.asText(), datatype)) {
                    errors.add("Value " + valueNode.asText() + " from possible values" + " does not match datatype " + datatype + " for column name " + columnName);
                }
            }
        }

        String range = conditionsNode.path("range").asText();
        if(!range.isEmpty()){
            if(!validateRange(range, datatype)){
                errors.add("Invalid range " + range + " for column name " + columnName);
            }
        }

        // Validate regex pattern
        String pattern = conditionsNode.path("pattern").asText();
        if (!pattern.isEmpty()) {
            try {
                Pattern.compile(pattern);
            } catch (Exception e) {
                errors.add("Invalid regex pattern: " + pattern + " for column name " + columnName);
            }
        }
    }

}
