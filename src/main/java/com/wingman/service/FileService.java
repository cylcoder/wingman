package com.wingman.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  @Value("${file.dir}")
  private String fileDir;

  public String upload(MultipartFile file) {
    try {
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
      String storeFilename = UUID.randomUUID() + "." + ext;
      file.transferTo(new File(fileDir + storeFilename));
      return "/file/" + storeFilename;
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  public void delete(String url) {
    if (url == null || !url.startsWith("/file/")) {
      throw new IllegalStateException();
    }

    String storeFilename = url.substring("/file/".length());
    Path filePath = Paths.get(fileDir + storeFilename);

    if (Files.exists(filePath)) {
      try {
        Files.delete(filePath);
      } catch (IOException e) {
        throw new IllegalStateException();
      }
    }
  }

  private String extractExt(String originalFilename) {
    int pos = originalFilename.lastIndexOf(".");
    return originalFilename.substring(pos + 1);
  }

}
