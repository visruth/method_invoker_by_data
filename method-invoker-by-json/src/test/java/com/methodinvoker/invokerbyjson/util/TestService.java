package com.methodinvoker.invokerbyjson.util;

import com.methodinvoker.invokerbyjson.data.User;

public class TestService {
    public String testServiceMethod(String s, Integer a) {
        System.out.println("testServiceMethod(String s " + s + ", int a " + a);
        return "returned testServiceMethod with two arguments " + s + " and "
                + a;
    }

    public String testServiceMethod(String s, Integer a, User u) {
        System.out.println("testServiceMethod(String s " + s + ", int a " + a
                + " u " + u);
        return "return value oftestServiceMethod with three arguments " + s
                + ", " + a + " and u " + u;
    }
}
