package com.example.storagekit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;

@Document
@AllArgsConstructor
@Getter
@Setter
public class Entry {

    @MongoId
    private ObjectId id;
    @Indexed
    @NotBlank
    private String key;
    @NotBlank
    private String value;
}
