package com.example.bachelor.service;

public interface SequenceGenerationService {

    /**
     * Der Service zur Erzeugung der ID für die MetaDaten.
     * @param seqName
     * @return die erzeugte fortlaufende ID
     */
    long generateSequence(String seqName);

}
