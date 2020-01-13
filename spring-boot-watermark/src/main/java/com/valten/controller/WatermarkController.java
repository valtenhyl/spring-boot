package com.valten.controller;

import com.valten.model.ImageInfo;
import com.valten.service.ImageUploadService;
import com.valten.service.WatermarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

@RestController
public class WatermarkController {

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private WatermarkService watermarkService;

    @RequestMapping(value = "/watermarktest", method = RequestMethod.POST)
    public ImageInfo watermarkTest(@RequestParam("file") MultipartFile image) {

        ImageInfo imgInfo = new ImageInfo();

        // 服务器上上传文件的相对路径
        String uploadPath = "static/images/";
        // 服务器上上传文件的物理路径
        String physicalUploadPath = Objects.requireNonNull(getClass().getClassLoader().getResource(uploadPath)).getPath();

        String imageUrl = imageUploadService.uploadImage(image, uploadPath, physicalUploadPath);
        File imageFile = new File(physicalUploadPath + image.getOriginalFilename());

        String watermarkAddImageUrl = watermarkService.watermarkAdd(imageFile, image.getOriginalFilename(), uploadPath, physicalUploadPath);

        imgInfo.setImageUrl(imageUrl);
        imgInfo.setLogoImageUrl(watermarkAddImageUrl);
        return imgInfo;
    }
}
