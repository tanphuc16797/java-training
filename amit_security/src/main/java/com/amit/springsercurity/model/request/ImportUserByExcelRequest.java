package com.amit.springsercurity.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImportUserByExcelRequest {
    private MultipartFile file;
}
