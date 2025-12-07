package com.wingman.controller;

import com.wingman.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileController {

  private final FileService fileService;

  @PostMapping("/files")
  public String upload(MultipartFile file) {
    return fileService.upload(file);
  }

}
