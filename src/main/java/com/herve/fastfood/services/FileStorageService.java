package com.herve.fastfood.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file, String fileName) {
        try {
            if (file != null && !file.isEmpty()) {
                if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
                    throw new RuntimeException("Le fichier doit être une image");
                }
            }

            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String finalFileName = fileName + "." + extension;
            Path targetLocation = Paths.get(uploadDir).resolve(finalFileName);

            Files.createDirectories(targetLocation.getParent());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + finalFileName;
        } catch (IOException e) {
            throw new RuntimeException("Échec de l'upload du fichier", e);
        }
    }

}
