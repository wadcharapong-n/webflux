package com.education.webflux.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.util.ObjectUtils;

@Data
abstract class IdentityAbstract {

    @Id
    ObjectId id;

    public String getId() {
        return !ObjectUtils.isEmpty(id) ? id.toHexString():null;
    }

    @JsonIgnore
    public ObjectId getObjectId() {
        return id;
    }
}
