package io.dataSynthAI.dataSynthAI_backend.controller;

import com.networknt.schema.ValidationMessage;
import io.dataSynthAI.dataSynthAI_backend.service.DataSynthCustomValidationService;
import io.dataSynthAI.dataSynthAI_backend.service.DataSynthReqValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;


@RestController
@Slf4j
@RequestMapping("/dataSynth")
public class DataSynthAIController {
    private final DataSynthReqValidationService dataSynthReqValidationService;

    private final DataSynthCustomValidationService dataSynthCustomValidationService;
    public DataSynthAIController(DataSynthReqValidationService dataSynthReqValidationService,
                                 DataSynthCustomValidationService dataSynthCustomValidationService){
        this.dataSynthReqValidationService = dataSynthReqValidationService;
        this.dataSynthCustomValidationService = dataSynthCustomValidationService;
    }

    /*@PostMapping("/validateJson")
    public Set<ValidationMessage> validateJson(@RequestBody String json) throws IOException {
        return dataSynthReqValidationService.validateJson(json);
    }*/

    @GetMapping("/validateSchema")
    public ResponseEntity<Set<String>> checkDataSynthSchema() throws IOException {
        log.info("Inside DataSynthAIController : checkDataSynthSchema() ");
        //dataSynthReqValidationService.inputSchemaLevelValidate();
        //dataSynthReqValidationService.validateJson("");
        //jsonCustomValidationService.validateJson("");
        Set<String> err = dataSynthCustomValidationService.validateDataSynthSchema();
        if(err.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/test")
    public String test(){
        return HttpStatus.OK.toString();
    }
}
