/*
 * Copyright [2014] [web firm]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author Visruth CV
 */
package com.methodinvoker.invokerbyjson.util;

import java.io.ByteArrayOutputStream;

/**
 *
 * @author Visruth CV
 * @since 1.0.0
 */
public class JsonBsonUtil {

    private JsonBsonUtil() {
        throw new AssertionError();
    }

    /**
     * converts json bytes to bson bytes
     *
     * @param json
     * @return
     */
    public static byte[] convertJsonToBson(byte[] json) {

        try {
            org.codehaus.jackson.map.ObjectMapper jsonMapper = new org.codehaus.jackson.map.ObjectMapper();
            com.fasterxml.jackson.databind.ObjectMapper bsonMapper = new com.fasterxml.jackson.databind.ObjectMapper(
                    new de.undercouch.bson4jackson.BsonFactory());

            Object javaObject = jsonMapper.readValue(json, Object.class);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bsonMapper.writeValue(baos, javaObject);
            byte[] bsonBytes = baos.toByteArray();

            return bsonBytes;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "The given json bytes may be invalid", e);
        }

    }

    /**
     * converts bson bytes to json bytes
     *
     * @param bson
     * @return
     */
    public static byte[] convertBsonToJson(byte[] bson) {

        try {
            org.codehaus.jackson.map.ObjectMapper jsonMapper = new org.codehaus.jackson.map.ObjectMapper();
            com.fasterxml.jackson.databind.ObjectMapper bsonMapper = new com.fasterxml.jackson.databind.ObjectMapper(
                    new de.undercouch.bson4jackson.BsonFactory());

            Object javaObject = bsonMapper.readValue(bson, Object.class);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            jsonMapper.writeValue(baos, javaObject);
            byte[] jsonBytes = baos.toByteArray();

            return jsonBytes;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "The given bson bytes may be invalid", e);
        }

    }

}
