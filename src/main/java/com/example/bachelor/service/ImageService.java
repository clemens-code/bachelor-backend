package com.example.bachelor.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface ImageService {

    /**
     *
     * @param image Das Bild, welches über die REST-Schnittstelle empfangen wurde
     * @return Gibt den gesamten Pfad des Bildes zurück, wenn das Speichern erfolgreich war. Andernfalls wird eine Exception geworfen.
     */
    Path saveImage(MultipartFile image) throws Exception;

    byte[] getImage(Path path) throws IOException;

}
