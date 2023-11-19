package com.ronyelison.locadora.integration.controller.yaml.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;

public class YAMLMapper implements ObjectMapper {

    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;
    protected TypeFactory typeFactory;

    public YAMLMapper(TypeFactory typeFactory) {
        this.objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.typeFactory = TypeFactory.defaultInstance();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object deserialize(ObjectMapperDeserializationContext context) {
        try {
            String data = context.getDataToDeserialize().asString();

            Class type = (Class) context.getType();
            return objectMapper.readValue(data, typeFactory.constructType(type));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object serialize(ObjectMapperSerializationContext context) {
        try {
            return objectMapper.writeValueAsString(context.getObjectToSerialize());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
