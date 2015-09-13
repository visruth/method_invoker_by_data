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
