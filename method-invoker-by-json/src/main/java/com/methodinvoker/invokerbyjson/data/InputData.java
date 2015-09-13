package com.methodinvoker.invokerbyjson.data;

import java.io.Serializable;

/**
 * @author visruth
 *
 */
public class InputData implements Serializable {

    private static final long serialVersionUID = 100L;

    private Class<?> methodClass;

    private String methodName;

    private Class<?>[] methodArgumentTypes;

    private Object[] methodJsonArguments;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getMethodArgumentTypes() {
        return methodArgumentTypes;
    }

    public void setMethodArgumentTypes(Class<?>... methodArgumentTypes) {
        this.methodArgumentTypes = methodArgumentTypes;
    }

    public Class<?> getMethodClass() {
        return methodClass;
    }

    public void setMethodClass(Class<?> methodClass) {
        this.methodClass = methodClass;
    }

    /**
     * @return array containing json string
     */
    public Object[] getMethodJsonArguments() {
        return methodJsonArguments;
    }

    public void setMethodJsonArguments(Object... methodJsonArguments) {
        this.methodJsonArguments = methodJsonArguments;
    }

}
