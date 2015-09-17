package com.methodinvoker.invokerbyjson.data;

import java.io.Serializable;

public class Result extends AbstractResult implements Serializable {

    private static final long serialVersionUID = 100L;

    private Class<?> methodClass;

    private String methodName;

    private Class<?>[] methodArgumentTypes;

    private Object[] methodArguments;

    private Class<?> methodReturnType;

    public Class<?> getMethodClass() {
        return methodClass;
    }

    public void setMethodClass(Class<?> methodClass) {
        this.methodClass = methodClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getMethodArgumentTypes() {
        return methodArgumentTypes;
    }

    public void setMethodArgumentTypes(Class<?>[] methodArgumentTypes) {
        this.methodArgumentTypes = methodArgumentTypes;
    }

    public Object[] getMethodArguments() {
        return methodArguments;
    }

    public void setMethodArguments(Object[] methodArguments) {
        this.methodArguments = methodArguments;
    }

    public Class<?> getMethodReturnType() {
        return methodReturnType;
    }

    public void setMethodReturnType(Class<?> methodReturnType) {
        this.methodReturnType = methodReturnType;
    }

}
