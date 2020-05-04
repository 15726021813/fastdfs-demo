package com.xcl.fastdfsspringboot2.controller;

import com.xcl.fastdfsspringboot2.utils.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * UploadController
 *
 * @author 徐长乐
 * @date 2020/5/4
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @RequestMapping("doUpload")
    public String  upload(MultipartFile mf){
        return uploadService.uploadImage(mf);
    }
}