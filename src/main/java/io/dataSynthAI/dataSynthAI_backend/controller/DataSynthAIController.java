package io.dataSynthAI.dataSynthAI_backend.controller;

import io.dataSynthAI.dataSynthAI_backend.model.DataSynthModel;
import io.dataSynthAI.dataSynthAI_backend.service.DataSynthCustomValidationService;
import io.dataSynthAI.dataSynthAI_backend.service.DataSynthRequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@Slf4j
@RequestMapping("/dataSynth")
public class DataSynthAIController {
    private final DataSynthRequestService dataSynthRequestService;

    private final DataSynthCustomValidationService dataSynthCustomValidationService;
    public DataSynthAIController(DataSynthRequestService dataSynthRequestService,
                                 DataSynthCustomValidationService dataSynthCustomValidationService){
        this.dataSynthRequestService = dataSynthRequestService;
        this.dataSynthCustomValidationService = dataSynthCustomValidationService;
    }

    @CrossOrigin
    @PostMapping("/validateAndProcess")
    public ResponseEntity<Set<String>> validateAndProcess(@RequestBody DataSynthModel dataSynthModel) {
        log.info("Inside DataSynthAIController : validateAndProcess()");
        try{
            boolean isValidationEligible = dataSynthRequestService.isValidationEligible(dataSynthModel.getFileName());
            if(isValidationEligible){
                Set<String> err = dataSynthCustomValidationService.validateDataSynthSchema(dataSynthModel.getFileData());
                if(err.isEmpty()){
                    return new ResponseEntity<>(HttpStatus.OK);
                }else{
                    return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
                }
            }else{
                dataSynthRequestService.processDataSynthRequest(dataSynthModel);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(Set.of(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/test")
    public String test(){
        //Set<String> errors = dataSynthCustomValidationService.validateDataSynthSchema();
        return HttpStatus.OK.toString();
    }
}
