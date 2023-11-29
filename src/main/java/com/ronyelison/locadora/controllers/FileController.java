package com.ronyelison.locadora.controllers;

import com.ronyelison.locadora.dto.UploadFileResponseDTO;
import com.ronyelison.locadora.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/file")
@Tag(name = "File Endpoint")
public class FileController {
    private FileStorageService fileStorageService;

    @Autowired
    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file){
        String filename = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/file/download/").path(filename).toUriString();
        return new UploadFileResponseDTO(filename, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponseDTO> uploadMultipleFile(@RequestParam("files") MultipartFile [] files){
        return Arrays.stream(files).map(this::uploadFile).toList();
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request){
        Resource resource = fileStorageService.loadFileAsResource(filename);
        String contentType = fileStorageService.getContentType(request,resource);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + resource.getFilename())
                .body(resource);
    }
}
