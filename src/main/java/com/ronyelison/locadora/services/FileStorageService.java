package com.ronyelison.locadora.services;

import com.ronyelison.locadora.config.FileStorageConfig;
import com.ronyelison.locadora.services.exceptions.FileStorageException;
import com.ronyelison.locadora.services.exceptions.MyFileNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        }catch (Exception e){
            throw new FileStorageException("Diretório de upload não foi criado!", e);
        }
    }

    public String storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")){
                throw new FileStorageException("Nome do arquivo é inválido! " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }catch (Exception e){
            throw new FileStorageException(fileName + " não encontrado. Tente novamente!");
        }
    }

    public Resource loadFileAsResource(String filename){
        try{
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("Arquivo não encontrado!");
            }
        } catch (Exception e){
            throw new MyFileNotFoundException("Arquivo não encontrado!", e);
        }
    }

    public String getContentType(HttpServletRequest request, Resource resource){
        String contentType = "";
        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e){
            System.out.println("Tipo do arquivo não foi identificado!");
        }

        if (contentType.isBlank()){
            contentType = "application/octet-stream";
        }
        return contentType;
    }
}
