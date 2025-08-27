package com.sc.demo.repo;

import org.mapstruct.*;
import com.sc.demo.dto.CustomerDTO;
import com.sc.demo.dto.ImageService;
import com.sc.demo.model.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;


@Mapper(componentModel = "spring", uses = { ImageService.class })
public interface MapperClass {

    @Mapping(target = "base64", source = "image", qualifiedByName = "toBase64")
    CustomerDTO toDto(Customer customer);

    @InheritInverseConfiguration
    @Mapping(target = "image", source = "base64", qualifiedByName = "toImage")
    Customer toEntity(CustomerDTO customerDTO);
    
    @Mapping(target = "id", ignore = true)
    void updateCustomerFromDto(CustomerDTO dto, @MappingTarget Customer entity);
}



//@Mapper(componentModel = "spring")
//public interface MapperClass {
//
//    // Entity → DTO (file path → Base64 string)
//    @Mapping(target = "base64", source = "image", qualifiedByName = "mapToBase64")
//    CustomerDTO toDto(Customer customer);
//
//    // DTO → Entity (Base64 string → file path)
//    @InheritInverseConfiguration
//    @Mapping(target = "image", source = "base64", qualifiedByName = "mapToImage")
//    Customer toEntity(CustomerDTO customerDTO);
//
//    void updateCustomerFromDto(CustomerDTO dto, @MappingTarget Customer entity);
//
//    // ----------- Custom Logic -------------
//
//    @Named("mapToBase64")
//    static String mapToBase64(String imagePath) {
//        if (imagePath == null || imagePath.isEmpty()) return null;
//        try {
//            byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
//            String extension = imagePath.substring(imagePath.lastIndexOf(".") + 1).toLowerCase();
//            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//            return "data:image/" + extension + ";base64," + base64Image;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Named("mapToImage")
//    static String mapToImage(String base64) {
//        if (base64 == null || base64.isEmpty()) return null;
//
//        try {
//            String extension = "png";
//            String imageData = base64;
//
//            if (base64.contains(",")) {
//                String[] parts = base64.split(",");
//                String metaInfo = parts[0];
//                imageData = parts[1];
//                if (metaInfo.contains("image/")) {
//                    extension = metaInfo.substring(metaInfo.indexOf("/") + 1, metaInfo.indexOf(";"));
//                }
//            }
//
//            byte[] imageBytes = Base64.getDecoder().decode(imageData);
//            String imageName = "customer_" + System.currentTimeMillis() + "." + extension;
//            String uploadDir = "uploads/";
//
//            java.io.File uploadFolder = new java.io.File(uploadDir);
//            if (!uploadFolder.exists()) {
//                uploadFolder.mkdirs();
//            }
//
//            String filePath = uploadDir + imageName;
//            try (java.io.OutputStream os = new java.io.FileOutputStream(filePath)) {
//                os.write(imageBytes);
//            }
//
//            return filePath;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
