package com.wingman.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  @Value("${file.dir}")
  private String fileDir;

  public String upload(MultipartFile file) throws IOException {
    if (file == null || file.isEmpty()) {
      throw new IllegalStateException();
    }

    File directory = new File(fileDir);

    if (!directory.exists()) {
      directory.mkdirs();
    }

    String originalFilename = file.getOriginalFilename();
    if (originalFilename == null) {
      throw new IllegalStateException();
    }

    String ext = extractExt(originalFilename);
    String storeFilename = UUID.randomUUID().toString() + "." + ext;

    file.transferTo(new File(fileDir + storeFilename));

    return "/files/" + storeFilename;
  }

  private String extractExt(String originalFilename) {
    int pos = originalFilename.lastIndexOf(".");
    return originalFilename.substring(pos + 1);
  }

}
