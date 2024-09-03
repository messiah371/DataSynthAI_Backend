package io.dataSynthAI.dataSynthAI_backend.model;


import lombok.Data;

@Data
public class DataSynthModel {

    private String modelName;
    private String outputFormat;
    private String fileName;
    private String fileData;

}
