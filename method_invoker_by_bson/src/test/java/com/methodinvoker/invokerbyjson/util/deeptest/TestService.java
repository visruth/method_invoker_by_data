package com.methodinvoker.invokerbyjson.util.deeptest;


public interface TestService {
    
    //custom
    public abstract TestBean testMethod(
            TestBean testBean);
    
    public abstract TestBean testMethod(java.sql.Timestamp timestamp, int intValue, String stringValue,
            float floatValue, double doubleValue, boolean booleanValue,
            char charValue, short shortValue, long longValue, byte byteValue,
            TestBean testBean);
    

}
