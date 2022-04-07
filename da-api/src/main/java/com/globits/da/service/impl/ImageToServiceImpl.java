package com.globits.da.service.impl;

import com.globits.core.domain.FileDescription;
import com.globits.core.dto.FileDescriptionDto;
import com.globits.core.service.FileDescriptionService;
import com.globits.da.service.ImageToService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


@Service
public class ImageToServiceImpl implements ImageToService {

    @Value("${upload.path}")
    private String fileUpload;

    @Autowired
    FileDescriptionService fileDescriptionService;

    @Override
    public FileDescriptionDto uploadImage(MultipartFile file) {
        FileDescriptionDto fileDescriptionDto = null;

        try {
            String pattern = "MM_dd_yyyy_HH_mm_ss_SSS";
            DateFormat df = new SimpleDateFormat(pattern);
            Date date = Calendar.getInstance().getTime();
            df.format(date);
            String fileName = UUID.randomUUID().toString()+".png";
            String filePath = Paths.get(fileUpload).toString();
                Path checkFolderExist = Paths.get(fileUpload);
                if (Files.exists(checkFolderExist))
                 {
                    Files.createDirectories(Paths.get(filePath));
                }
                File fileToBeSaved = new File(filePath, fileName);
                file.transferTo(fileToBeSaved);
                FileDescription files = new FileDescription();
                files.setContentSize(file.getSize());
                files.setContentType(file.getContentType());
                files.setName(fileName);
                files.setFilePath(filePath);
                files = fileDescriptionService.save(files);
                fileDescriptionDto = new FileDescriptionDto(files);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileDescriptionDto;
    }


  
}
