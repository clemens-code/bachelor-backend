package com.example.bachelor.entities.response;

import com.example.bachelor.entities.metadata.MetaData;
import org.springframework.web.multipart.MultipartFile;

public class ResponseDataWithImageObject {

    private byte[] imageInBytes;
    private MetaData metaData;

    public ResponseDataWithImageObject(byte[] imageInBytes, MetaData metaData) {
        this.imageInBytes = imageInBytes;
        this.metaData = metaData;
    }

    public byte[] getImageInBytes() {
        return imageInBytes;
    }

    public void setImageInBytes(byte[] imageInBytes) {
        this.imageInBytes = imageInBytes;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
}
