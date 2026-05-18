package com.javabuider.user_service.client;

import com.javabuider.user_service.dto.response.ApiResponse;
import com.javabuider.user_service.dto.response.FileResponse;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange()
public interface MediaClient {

    @PostExchange("/api/v1/s3/upload")
    ApiResponse<FileResponse> uploadFile(@RequestPart("file") MultipartFile file);

}