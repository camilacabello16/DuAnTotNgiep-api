package com.globits.da.rest;

import com.globits.core.dto.FileDescriptionDto;
import com.globits.da.service.ImageToService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

import org.apache.commons.io.IOUtils;

@Controller
@RequestMapping("/api/upload-file")
public class RestUploadFileController {

    @Autowired
    ImageToService imageToService;

    @Autowired
    private Environment env;

    @PostMapping
    public ResponseEntity<FileDescriptionDto> uploadImage(@RequestParam("file") MultipartFile file) {
        FileDescriptionDto result = null;
        try {
            result = imageToService.uploadImage(file);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(path = "/{filename}", method = RequestMethod.GET)
    public void getImage(HttpServletResponse response, @PathVariable String filename) throws IOException {
        String path = "";
        if (env.getProperty("upload.path") != null) {
            path = env.getProperty("upload.path");
        }
        File file = new File(path + filename + ".png");
        if (file.exists()) {
            String contentType = "application/octet-stream";
            response.setContentType(contentType);
            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(file);
            IOUtils.copy(in, out);
            out.close();
            in.close();
        } else {
            throw new FileNotFoundException();
        }
    }
}
