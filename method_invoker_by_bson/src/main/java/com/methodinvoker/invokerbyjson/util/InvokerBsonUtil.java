package com.methodinvoker.invokerbyjson.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.methodinvoker.invokerbybson.data.InputData;
import com.methodinvoker.invokerbybson.data.Result;

import de.undercouch.bson4jackson.BsonFactory;

public class InvokerBsonUtil {

    private InvokerBsonUtil() {
        throw new AssertionError();
    }

    /**
     * Invokes the corresponding method from the class given by the bson bytes.
     *
     * @param map
     *            containing key as the class of the object passed as the value
     *            and the value as the object of the given key class.
     * @param jsonString
     * @return the return value of the method as a bson bytes or {@code null} if
     *         the method return type is void or the method returns null.
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @author visruth
     */
    public static byte[] invokeAndReturnBson(Map<Class<?>, Object> map,
            byte[] bson) throws NoSuchMethodException, IllegalArgumentException {
        Object result = invoke(map, bson);
        if (result != null) {
            ObjectMapper mapper = new ObjectMapper(new BsonFactory());
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                mapper.writeValue(baos, result);
                return baos.toByteArray();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return null;
    }

    /**
     * Invokes the corresponding method from the class given by the bson bytes.
     *
     * @param map
     *            containing key as the class of the object passed as the value
     *            and the value as the object of the given key class.
     * @param bson
     * @return the return value of the method or {@code null} if the method
     *         return type is void.
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @author visruth
     */
    public static Object invoke(Map<Class<?>, Object> map, byte[] bson)
            throws NoSuchMethodException, IllegalArgumentException {

        try {

            ObjectMapper mapper = new ObjectMapper(new BsonFactory());
            InputData inputDataParsed = mapper.readValue(bson, InputData.class);

            Class<?> methodClass = inputDataParsed.getMethodClass();
            Object object = map.get(methodClass);

            Class<?>[] methodArgumentTypes = inputDataParsed
                    .getMethodArgumentTypes();
            Object[] methodJsonArguments = inputDataParsed
                    .getMethodJsonArguments();
            Object[] methodArguments = new Object[methodArgumentTypes.length];

            for (int i = 0; i < methodArgumentTypes.length; i++) {
                Object methodArg = methodJsonArguments[i];
                if (methodArg != null) {
                    if (methodArgumentTypes[i] == String.class) {
                        methodArguments[i] = methodArg.toString();
                    } else {
                        methodArguments[i] = mapper.readValue(methodArg
                                .toString().getBytes(StandardCharsets.UTF_8),
                                methodArgumentTypes[i]);
                    }
                } else {
                    methodArguments[i] = methodArg;
                }
            }
            Method method = methodClass.getMethod(
                    inputDataParsed.getMethodName(), methodArgumentTypes);
            Object result = method.invoke(object, methodArguments);
            // if (result != null) {
            // return mapper.writeValueAsString(result);
            // }
            return result;
        } catch (NoSuchMethodException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Invokes the corresponding method from the class given by the bson bytes.
     *
     * @param map
     *            containing key as the class of the object passed as the value
     *            and the value as the object of the given key class.
     * @param bson
     * @return the return the object of {Result} containing the method
     *         invocation details.
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @author visruth
     */
    public static Result invokeAndReturnResult(Map<Class<?>, Object> map,
            byte[] bson) throws NoSuchMethodException, IllegalArgumentException {

        try {

            ObjectMapper mapper = new ObjectMapper(new BsonFactory());
            InputData inputDataParsed = mapper.readValue(bson, InputData.class);

            Class<?> methodClass = inputDataParsed.getMethodClass();
            Object object = map.get(methodClass);

            Class<?>[] methodArgumentTypes = inputDataParsed
                    .getMethodArgumentTypes();
            Object[] methodJsonArguments = inputDataParsed
                    .getMethodJsonArguments();
            Object[] methodArguments = new Object[methodArgumentTypes.length];

            for (int i = 0; i < methodArgumentTypes.length; i++) {
                Object methodArg = methodJsonArguments[i];
                if (methodArg != null) {
                    if (methodArgumentTypes[i] == String.class) {
                        methodArguments[i] = methodArg.toString();
                    } else {
                        methodArguments[i] = mapper.readValue(methodArg
                                .toString().getBytes(StandardCharsets.UTF_8),
                                methodArgumentTypes[i]);
                    }
                } else {
                    methodArguments[i] = methodArg;
                }
            }
            String methodName = inputDataParsed.getMethodName();
            Method method = methodClass.getMethod(methodName,
                    methodArgumentTypes);

            Result result = new Result();
            result.setMethodArgumentTypes(methodArgumentTypes);
            result.setMethodArguments(methodArguments);
            result.setMethodClass(methodClass);
            result.setMethodName(methodName);
            result.setMethodReturnType(method.getReturnType());
            try {
                result.setMethodReturnValue(method.invoke(object,
                        methodArguments));
            } catch (Throwable e) {
                result.setMethodException(e);
            }

            return result;
        } catch (NoSuchMethodException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
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
    public static byte[] convertToBsonJson(byte[] bson) {

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
