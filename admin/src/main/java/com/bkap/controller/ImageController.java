package com.bkap.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 11/08/2020 - 15:05
 * @created_by Tung lam
 * @since 11/08/2020
 */
@RestController
public class ImageController {
    // config add image product
    @PostMapping(value = "/image")
    public ResponseEntity addImage(@RequestParam("fileup")
                                           MultipartFile file, HttpServletRequest request) throws IOException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dlit4g4xt",
                "api_key", "629398549247116",
                "api_secret", "sHHc9bLuVrv5mExjz3taeymd1Ys"));
        byte[] a = file.getBytes();
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        return new ResponseEntity(uploadResult.get("url"), HttpStatus.OK);
    }
}
