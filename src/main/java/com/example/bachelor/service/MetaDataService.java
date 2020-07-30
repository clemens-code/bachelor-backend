package com.example.bachelor.service;

import com.example.bachelor.dto.metadata.ReturnMetaData;
import com.example.bachelor.entities.metadata.MetaData;
import org.springframework.lang.Nullable;
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
     * @throws UnsupportedOperationException - Wenn die ID bereits vergeben ist
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
    List<ReturnMetaData> getAllMetaData(HttpServletRequest httpServletRequest);

    /**
     *
     * @param httpServletRequest - ServletRequest um den Nutzer und die gewünschte Gruppe des Request zu wissen.
     * @return - Eine Liste aller MetaDaten für eine Gruppe eines Nutzers.
     */
    List<ReturnMetaData> getAllMetaDataByGroup(HttpServletRequest httpServletRequest);

    /**
     *
     * @param group - Die Gruppe, nach der gesucht werden soll
     * @param httpServletRequest - ServletRequest um den Nutzer und die gewünschte Gruppe des Request zu wissen.
     * @return - Eine Liste aller MetaDaten für eine Gruppe eines Nutzers.
     */
    List<ReturnMetaData> getAllMetaDataByGroup(String group, HttpServletRequest httpServletRequest);

    /**
     *
     * @param _id - Die ID des gwünschten Objekts.
     * @return - Ein Optional vom Typ MetaData. Dieses muss keinen Inhalt haben
     */
    Optional<ReturnMetaData> getMetaDataById(long _id, HttpServletRequest servletRequest);

    /**
     *
     * @param _id - Die ID der zulöschenden Entität.
     * @param servletRequest - Die Informationen der angekommenen Anfrage
     * @throws IllegalArgumentException - Wenn die Entität nicht existiert
     * @throws UnsupportedOperationException - Wenn der Nutzer nicht berechtigt ist die Eintität zu löschen
     * @throws UnknownError - Wenn das Löschen der Entität fehlschlägt
     * @throws UnknownError - Wenn das Löschen des Bildes fehlschlägt
     */
    void deleteEntityByID(long _id, HttpServletRequest servletRequest);

    /**
     *
     * @param _id - Die ID des gwünschten Objekts.
     * @return - Ein Objekt von Typ ResponseDataWithImageObject, welches das Bild und die MetaDaten enthält.
     */
    byte[] getImageForId(long _id, HttpServletRequest servletRequest);

    /**
     *
     * @param metaDataInformation - Die Informationen für ein Update
     * @param id - Die ID des zu ändernden Objekts
     * @param image - Das Bild für ein Update
     * @param servletRequest - Die Informationen der angekommenen Anfrage
     * @throws IllegalArgumentException - Wenn die Metadateninformationen und das Bild null sind
     * @throws UnsupportedOperationException - Wenn der Nutzer nicht berechtigt ist diese Aktion durchzuführen
     */
    void updateMetaData(Object metaDataInformation, long id,  MultipartFile image,
                               HttpServletRequest servletRequest);

    /**
     *
     * @param httpServletRequest - Die Informationen der angekommenen Anfrage
     * @return - Den Namen des Nutzers, welcher die Anfrage gesendet hat
     */
    String getUserFromRequest(HttpServletRequest httpServletRequest);

    ReturnMetaData mapMetaDataOnReturnMetaData(MetaData metaData);
}
