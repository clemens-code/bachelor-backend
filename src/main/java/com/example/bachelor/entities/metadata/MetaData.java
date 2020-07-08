package com.example.bachelor.entities.metadata;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Document(collection = "testMongo")
public class MetaData {

    @Id
    @GeneratedValue
    public Long _id;

    public Object information;

    public String path;

    public MetaData(){}

    public MetaData(Object information, String path) {
        this.information = information;
        this.path = path;
    }

    public MetaData(Long id, Object information, String path) {
        this._id = id;
        this.information = information;
        this.path = path;
    }

    public Long get_id() {
        return _id;
    }

    public Object getInformation() {
        return information;
    }

    public void setInformation(Object information) {
        this.information = information;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
