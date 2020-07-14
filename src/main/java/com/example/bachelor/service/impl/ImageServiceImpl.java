package com.example.bachelor.service.impl;

import com.example.bachelor.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Value("${directory.base.path}")
    private String ROOT_LOCATION;

    @Override
    public Path saveImage(MultipartFile image) throws IOException {

        Objects.requireNonNull(image, "The image is not allowed to be null");

        SimpleDateFormat formatDay = new SimpleDateFormat("ddMMyyyy");
        Path imageDirectory = Paths.get(ROOT_LOCATION, formatDay.format(Calendar.getInstance().getTime()));
        if(!Files.exists(imageDirectory))
        {

            LOG.info("Trying to create the BaseDir for the Images {}", imageDirectory);
            Files.createDirectories(imageDirectory);
        }
        LOG.info("The directory for the images all ready exits or was just created successfully!");
        Path imageAddress = Paths.get(imageDirectory.toString(),System.currentTimeMillis() +".jpg");
        try (InputStream inputStream = image.getInputStream()) {
            Files.copy(inputStream, imageAddress);
        }
        return imageAddress;
    }

    @Override
    public byte[] getImage(Path path) throws  IOException{
        Objects.requireNonNull(path,"The Path of the Image can't be null!");
        LOG.info("Trying to read image from System.");
        return Files.readAllBytes(path);
    }
}
