package com.app.quest.uploadImages;

import com.app.quest.common.S3ImageUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import scala.xml.Null;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class UploadImageController {

    @Autowired
    private S3ImageUploader s3ImageUploader;

    @Autowired
    private UploadedImageService uploadedImageService;

    @PostMapping
    public ResponseEntity uploadImage(@RequestParam("data")MultipartFile file) throws IOException {
        if (!s3ImageUploader.validateType(file)) {
            return ResponseEntity.badRequest().build();
        }
        UploadedImageDto dto = s3ImageUploader.upload(file, "static");

        UploadedImage savedImage = uploadedImageService.save(dto);

        return ResponseEntity.ok(new UploadedImageDto(savedImage));
    }
}
