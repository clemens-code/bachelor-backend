package com.example.bachelor.entities.metadata;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

@Document
public class MetaData {

    @Id
    @GeneratedValue
    public Long id;

    public String information;

    public String path;

    public MetaData(){}

    public MetaData(String information, String path) {
        this.information = information;
        this.path = path;
    }

    public MetaData(Long id, String information, String path) {
        this.id = id;
        this.information = information;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
