package com.sc.demo.dto;



import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class ImageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    // File path -> Base64
    @Named("toBase64")
    public String toBase64(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) return null;
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
            ///uploads/customer123.png"
            String extension = imagePath.substring(imagePath.lastIndexOf(".") + 1).toLowerCase();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
          //data:image/webp;base64,UklGRqAnA
            return "data:image/" + extension + ";base64," + base64Image;
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert image to Base64", e);
        }
    }

    // Base64 -> File path
    @Named("toImage")
    public String saveBase64Image(String base64) {
        if (base64 == null || base64.isEmpty()) {//return null; {
        	 throw new RuntimeException("Base64 image data is empty or null");
        }
//
        try {
            String extension = "png";
            String imageData = base64;	
            
          //data:image/webp;base64,UklGRqAnA
            
            if (base64.contains(",")) {
                String[] parts = base64.split(",");
                String metaInfo = parts[0];
                imageData = parts[1];
                if (metaInfo.contains("image/")) {
                    extension = metaInfo.substring(metaInfo.indexOf("/") + 1, metaInfo.indexOf(";"));
                }
            }

            byte[] imageBytes = Base64.getDecoder().decode(imageData);
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String imageName = "customer_" + System.currentTimeMillis() + "." + extension;
//            String filePath = uploadDir + File.separator + imageName;
            String filePath = uploadDir + imageName;

            try (OutputStream os = new FileOutputStream(filePath)) {
                os.write(imageBytes);
            }

            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert Base64 to image file", e);
        }
    }
    

    // Delete old file
    public void deleteFile(String path) {
        if (path != null && !path.isEmpty()) {
            File file = new File(path);
            if (file.exists()) file.delete();
        }
    }
}

