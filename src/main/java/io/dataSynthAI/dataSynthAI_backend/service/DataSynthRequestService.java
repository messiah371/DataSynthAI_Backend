package io.dataSynthAI.dataSynthAI_backend.service;

import io.dataSynthAI.dataSynthAI_backend.model.DataSynthModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class DataSynthRequestService {
    private final RestTemplate restTemplate;
    public DataSynthRequestService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public boolean isValidationEligible(String fileName) {
        return StringUtils.isNotBlank(fileName) && fileName.contains(".json");
    }

    public void processDataSynthRequest(DataSynthModel dataSynthModel){
        try{
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DataSynthModel> httpEntity = new HttpEntity<>(dataSynthModel, httpHeaders);
            log.info("Processing request to DataSynthAI Engine with dataSynthModel {}", dataSynthModel);
            restTemplate.postForEntity("", httpEntity, DataSynthModel.class);
        }catch(Exception ex){
            log.info("Exception occurred in processDataSynthRequest for file name {}", dataSynthModel.getFileName());
            throw new RestClientException("Rest Processing Exception in processing request to DataSynthAI Engine");
        }
    }
}
