package com.methodinvoker.invokerbyjson.util.deeptest;


public class TestServiceImpl implements TestService {


    @Override
    public TestBean testMethod(TestBean testBean) {
        System.out.println("testMethod(testBean invoked ");
        return testBean;
    }

    @Override
    public TestBean testMethod(java.sql.Timestamp timestamp, int intValue,
            String stringValue, float floatValue, double doubleValue,
            boolean booleanValue, char charValue, short shortValue,
            long longValue, byte byteValue, TestBean testBean) {
        System.out.println("testMethod with many arguments invoked ");
        System.out.println("timestamp "+timestamp);
        return testBean;
    }

}
