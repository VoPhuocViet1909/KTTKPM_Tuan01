package com.javabuilder.mediaservice.service;

import com.javabuilder.mediaservice.dto.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;
import com.javabuilder.mediaservice.dto.response.PreSignedResponse;

import java.io.IOException;

public interface StorageService {
    FileResponse uploadFile(MultipartFile file) throws IOException;
    void deleteFile(String fileKey);
    PreSignedResponse generatePresignedUrl(String fileName);
}