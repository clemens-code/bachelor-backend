package com.example.bachelor.entities.metadata;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

public class MetaData {

    @Id
    @GeneratedValue
    public Long id;

    public Map<String, String> information;

    public String path;

    public MetaData(Long id){

    }
}
