package com.culturespot.culturespotdomain.core.global.infrastructure.storage.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface StorageService {
    List<String> uploadFiles(List<MultipartFile> images, Map<String, String> uuidToOriginalFileName);
    void deleteFile(String fileName);
    void deleteFiles(List<String> fileNames);
}
