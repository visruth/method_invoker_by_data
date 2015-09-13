package com.methodinvoker.invokerbyjson.util;

import java.lang.reflect.Method;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.methodinvoker.invokerbyjson.data.InputData;

/**
 * @author visruth
 *
 */
public class InvokerUtil {

    private InvokerUtil() {
        throw new AssertionError();
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
                        methodArguments[i] = mapper
                                .readValue(("\"" + methodArg.toString() + "\"")
                                        .getBytes(), methodArgumentTypes[i]);
                    } else {
                        methodArguments[i] = mapper.readValue(methodArg
                                .toString().getBytes(), methodArgumentTypes[i]);
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

}
