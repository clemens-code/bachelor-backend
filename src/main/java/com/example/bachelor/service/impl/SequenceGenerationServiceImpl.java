package com.example.bachelor.service.impl;

import com.example.bachelor.entities.metadata.DatabaseSequence;
import com.example.bachelor.service.SequenceGenerationService;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGenerationServiceImpl implements SequenceGenerationService {

    private MongoOperations mongoOperations;

    @Override
    public long generateSequence(String seqName) {
        DatabaseSequence sequence = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true), DatabaseSequence.class);
        return !Objects.isNull(sequence) ? sequence.getSeq() : 1;
    }

    @Resource
    public void setMongoOperations(MongoOperations mongoOperations){
        this.mongoOperations=mongoOperations;
    }
}
