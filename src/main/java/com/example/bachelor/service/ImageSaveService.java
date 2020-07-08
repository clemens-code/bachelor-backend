package com.example.bachelor.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ImageSaveService {

    /**
     *
     * @param image Das Bild, welches über die REST-Schnittstelle empfangen wurde
     * @return Gibt den gesamten Pfad des Bildes zurück, wenn das Speichern erfolgreich war. Andernfalls wird eine Exception geworfen.
     */
    public String saveImage(MultipartFile image) throws Exception;

}
