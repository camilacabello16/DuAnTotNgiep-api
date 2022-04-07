package com.globits.da.service;

import com.globits.core.dto.FileDescriptionDto;
import com.globits.core.service.GenericService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


public interface ImageToService {
        FileDescriptionDto uploadImage(MultipartFile file);


}
