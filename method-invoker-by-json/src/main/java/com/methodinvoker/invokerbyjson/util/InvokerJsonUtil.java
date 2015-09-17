/*
 * Copyright since 2015
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
 * @author visruth
 */
package com.methodinvoker.invokerbyjson.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.methodinvoker.invokerbyjson.data.InputData;
import com.methodinvoker.invokerbyjson.data.Result;

/**
 * @author visruth
 *
 */
public class InvokerJsonUtil {

    private InvokerJsonUtil() {
        throw new AssertionError();
    }

    /**
     * Invokes the corresponding method from the class given by the json string.
     *
     * @param map
     *            containing key as the class of the object passed as the value
     *            and the value as the object of the given key class.
     * @param jsonString
     * @return the return value of the method as a json string or {@code null}
     *         if the method return type is void or the method returns null.
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @author visruth
     */
    public static String invokeAndReturnJsonString(Map<Class<?>, Object> map,
            String jsonString) throws NoSuchMethodException,
            IllegalArgumentException {
        Object result = invoke(map, jsonString);
        if (result != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(result);
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return null;
    }

    /**
     * Invokes the corresponding method from the class given by the json string.
     *
     * @param map
     *            containing key as the class of the object passed as the value
     *            and the value as the object of the given key class.
     * @param jsonString
     * @return the return value of the method or {@code null} if the method
     *         return type is void.
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @author visruth
     */
    public static Object invoke(Map<Class<?>, Object> map, String jsonString)
            throws NoSuchMethodException, IllegalArgumentException {

        try {

            ObjectMapper mapper = new ObjectMapper();
            InputData inputDataParsed = mapper.readValue(jsonString,
                    InputData.class);

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
     * Invokes the corresponding method from the class given by the json string.
     *
     * @param map
     *            containing key as the class of the object passed as the value
     *            and the value as the object of the given key class.
     * @param jsonString
     * @return the return the object of {Result} containing the method
     *         invocation details.
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @author visruth
     */
    public static Result invokeAndReturnResult(Map<Class<?>, Object> map,
            String jsonString) throws NoSuchMethodException,
            IllegalArgumentException {

        try {

            ObjectMapper mapper = new ObjectMapper();
            InputData inputDataParsed = mapper.readValue(jsonString,
                    InputData.class);

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

}
