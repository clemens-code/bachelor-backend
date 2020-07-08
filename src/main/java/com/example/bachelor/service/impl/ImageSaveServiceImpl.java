package com.example.bachelor.service.impl;

import com.example.bachelor.service.ImageSaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

@Service
public class ImageSaveServiceImpl implements ImageSaveService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageSaveServiceImpl.class);

    @Value("${directory.base.path}")
    private String ROOT_LOCATION;

    @Override
    public String saveImage(MultipartFile image) throws IOException {

        Objects.requireNonNull(image, "The image is not allowed to be null");

        SimpleDateFormat formatDay = new SimpleDateFormat("ddMMyyyy");

        File imageBaseDirectory = new File(ROOT_LOCATION +"\\"+formatDay.format(Calendar.DATE));
        if(!imageBaseDirectory.exists())
        {
            LOG.info("Trying to create the BaseDir for the Images {}", imageBaseDirectory);
            boolean dirCreated = imageBaseDirectory.mkdir();
            if (! dirCreated) {
                throw new RuntimeException("The creation of the directory "+imageBaseDirectory +" was not possible!");
            }
        }
        LOG.info("The directory for the images all ready exits or was just created successfully!");
        File directoryOfImage = new File(imageBaseDirectory+"\\"+System.currentTimeMillis() +".jpg");
        try (InputStream inputStream = image.getInputStream()) {
            Files.copy(inputStream, Paths.get(ROOT_LOCATION, formatDay.format(Calendar.DATE), System.currentTimeMillis()+ ".jpg"));
        }
        return directoryOfImage.toString();
    }

}
