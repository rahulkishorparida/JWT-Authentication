//package com.sc.demo.dto;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Base64;
//
//public class ImageToBase64 {
//    public static void main(String[] args) {
//        try {
//            // Read image file into byte array
//            byte[] bytes = Files.readAllBytes(Paths.get("path/to/image.png"));
//
//            // Encode byte array to Base64
//            String base64 = Base64.getEncoder().encodeToString(bytes);
//
//            // Print or use the Base64 string
//            System.out.println("data:image/png;base64," + base64);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
