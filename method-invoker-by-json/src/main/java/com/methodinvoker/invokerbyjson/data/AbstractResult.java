package com.methodinvoker.invokerbyjson.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class AbstractResult {

    private Object methodReturnValue;

    private Throwable methodException;

    private byte[] methodReturnValueAsBson;

    private byte[] methodReturnValueAsJson;

    private String methodReturnValueAsJsonString;

    public byte[] getMethodReturnValueAsBson() {

        if (methodReturnValue == null) {
            return null;
        }

        if (methodReturnValueAsBson != null) {
            return methodReturnValueAsBson;
        }

        com.fasterxml.jackson.databind.ObjectMapper bsonMapper = new com.fasterxml.jackson.databind.ObjectMapper(
                new de.undercouch.bson4jackson.BsonFactory());

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bsonMapper.writeValue(baos, methodReturnValue);
            methodReturnValueAsBson = baos.toByteArray();
            return methodReturnValueAsBson;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    public byte[] getMethodReturnValueAsJson() {
        if (methodReturnValue == null) {
            return null;
        }

        if (methodReturnValueAsJson != null) {
            return methodReturnValueAsJson;
        }
        org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
        try {
            methodReturnValueAsJson = mapper
                    .writeValueAsBytes(methodReturnValue);
            return methodReturnValueAsJson;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getMethodReturnValueAsJsonString() {
        if (methodReturnValue == null) {
            return null;
        }

        if (methodReturnValueAsJsonString != null) {
            return methodReturnValueAsJsonString;
        }
        org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
        try {
            methodReturnValueAsJsonString = mapper
                    .writeValueAsString(methodReturnValue);
            return methodReturnValueAsJsonString;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Object getMethodReturnValue() {
        return methodReturnValue;
    }

    public void setMethodReturnValue(Object methodReturnValue) {
        this.methodReturnValue = methodReturnValue;
    }

    public Throwable getMethodException() {
        return methodException;
    }

    public void setMethodException(Throwable methodException) {
        this.methodException = methodException;
    }

}
