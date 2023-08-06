package de.maxkorte.inventorytracker.util;

import de.maxkorte.inventorytracker.exception.ImageDeletionException;
import de.maxkorte.inventorytracker.exception.ImageSavingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FileUtil {
    private FileUtil() {
    }

    public static List<String> saveImageFiles(List<MultipartFile> images) {
        return images.stream().filter(image -> !image.isEmpty()).map(FileUtil::saveImageFile).collect(Collectors.toList());
    }

    private static String saveImageFile(MultipartFile image) {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = Path.of(System.getProperty("user.home") + "/inventory-manager-img/", fileName);
        try {
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ImageSavingException();
        }
        return fileName;
    }

    public static void deleteImageFiles(List<String> imageFileNames) {
        for (String fileName : imageFileNames) {
            Path filePath = Path.of(System.getProperty("user.home") + "/inventory-manager-img", fileName);
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                throw new ImageDeletionException();
            }
        }
    }
}
