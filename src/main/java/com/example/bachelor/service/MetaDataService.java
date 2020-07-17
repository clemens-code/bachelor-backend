package com.example.bachelor.service;

import com.example.bachelor.entities.metadata.MetaData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface MetaDataService {

    /**
     *
     * @param metaDataInformation - Die Metadaten als Objekt.
     * @param id - Die ID, welche als Request Param gereicht wurde.
     * @param image - Das Bild zu den Metadaten.
     * @param servletRequest - Den ServletRequest um Gruppe und Nutzer zu wissen.
     * @return - Das gespeicherte Metadatenobjekt, dieses kann auch null sein, sollte beim Speichern etwas schiefgehen.
     *              Sollte beim Speichern des Bildes einen Fehler geben wird die IOException gefangen und null returned.
     */
    MetaData saveMetaData(Object metaDataInformation, long id, MultipartFile image, HttpServletRequest servletRequest);

    /**
     *
     * @param metaDataInformation - Die Metadaten als Objekt.
     * @param image - Das Bild zu den Metadaten.
     * @param servletRequest - Den ServletRequest um Gruppe und Nutzer zu wissen.
     * @return - Das gespeicherte Metadatenobjekt, dieses kann auch null sein, sollte beim Speichern etwas schiefgehen.
     *           Sollte beim Speichern des Bildes einen Fehler geben wird die IOException gefangen und null returned.
     */
    MetaData saveMetaData(Object metaDataInformation, MultipartFile image, HttpServletRequest servletRequest);

    /**
     *
     * @param httpServletRequest - ServletRequest um den Nutzer des Request zu wissen.
     * @return - Eine Liste aller MetaDaten für einen Nutzer.
     */
    List<MetaData> getAllMetaData(HttpServletRequest httpServletRequest);

    /**
     *
     * @param httpServletRequest - ServletRequest um den Nutzer und die gewünschte Gruppe des Request zu wissen.
     * @return - Eine Liste aller MetaDaten für eine Gruppe eines Nutzers.
     */
    List<MetaData> getAllMetaDataByGroup(HttpServletRequest httpServletRequest);

    /**
     *
     * @param _id - Die ID des gwünschten Objekts.
     * @return - Ein Optional vom Typ MetaData. Dieses muss keinen Inhalt haben
     */
    public Optional<MetaData> getMetaDataById(long _id, HttpServletRequest servletRequest);

    /**
     *
     * @param _id - Die ID des gwünschten Objekts.
     * @return - Ein Objekt von Typ ResponseDataWithImageObject, welches das Bild und die MetaDaten enthält.
     */

    byte[] getImageForId(long _id, HttpServletRequest servletRequest);

    String getUserFromRequest(HttpServletRequest httpServletRequest);
}
