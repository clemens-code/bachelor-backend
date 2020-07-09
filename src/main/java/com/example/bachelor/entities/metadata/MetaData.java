package com.example.bachelor.entities.metadata;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Meta;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Document(collection = "testMongo")
public class MetaData {

    public static class Builder {

        private long _id;
        private Object information;
        private String path;
        private String owner;
        private String group;

        public Builder(){}

        public Builder withID(long _id) {
            this._id = _id;

            return this;
        }

        public Builder withInformation(Object information) {
            this.information = information;

            return this;
        }

        public Builder withPath(String path) {
            this.path = path;

            return this;
        }

        public Builder withOwner(String owner){
            this.owner=owner;

            return this;
        }

        public Builder withGroup(String group){
            this.group=group;

            return this;
        }

        public MetaData build() {
            MetaData metaData = new MetaData();
            metaData._id = this._id;
            metaData.information = this.information;
            metaData.path = this.path;
            metaData.group=this.group;
            metaData.owner=this.owner;
            return metaData;
        }

    }

    @Transient
    public static final String SEQUENCE_NAME = "metadata_sequence";

    public Long _id;
    public Object information;
    public String path;
    public String owner;
    public String group;

    private MetaData() {
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}

    /*@Transient
    public static final String SEQUENCE_NAME = "metadata_sequence";

    @Id
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
    }*/
