package com.methodinvoker.invokerbyjson.util;

import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.methodinvoker.invokerbyjson.data.InputData;
import com.methodinvoker.invokerbyjson.data.Profile;
import com.methodinvoker.invokerbyjson.data.User;

public class InvokerUtilTest {

    @Test
    public void testName() throws Exception {

    }

    @Test
    public void testInvoke1() {

        URL resource = getClass().getResource(
                "testServiceMethod_2args_SampleJson.json");
        String path = resource.getPath();
        System.out.println(path);
        try (

        FileInputStream fis = new FileInputStream(Paths.get(resource.toURI())
                .toFile());

        ) {

            ByteBuffer byteBuffer = ByteBuffer.allocate(fis.available());

            byteBuffer.clear();
            fis.getChannel().read(byteBuffer);
            byteBuffer.flip();

            Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
            map.put(TestService.class, new TestService());

            String jsonString = new String(byteBuffer.array());

            try {
                Object returned = InvokerUtil.invoke(map, jsonString);

                System.out.println(returned);
                org.junit.Assert
                        .assertEquals(
                                "returned testServiceMethod with two arguments yes string and 55555",
                                returned);
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testInvoke2() {

        InputData inputData = new InputData();
        inputData.setMethodClass(TestService.class);
        inputData.setMethodName("testServiceMethod");
        inputData.setMethodArgumentTypes(String.class, Integer.class,
                User.class);

        User user = new User();
        user.setFirstName("First Name");
        Profile profile = new Profile();
        profile.setAddress("House No.1");
        user.setProfile(profile);
        ObjectMapper mapper1 = new ObjectMapper();

        URL resource = getClass().getResource("sample.json");
        String path = resource.getPath();
        System.out.println(path);
        try (

        FileInputStream fis = new FileInputStream(Paths.get(resource.toURI())
                .toFile());

        ) {

            ByteBuffer byteBuffer = ByteBuffer.allocate(fis.available());

            byteBuffer.clear();
            fis.getChannel().read(byteBuffer);
            byteBuffer.flip();

            Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
            map.put(TestService.class, new TestService());

            String jsonString = new String(byteBuffer.array());

            try {
                Object returned = InvokerUtil.invoke(map, jsonString);

                System.out.println(returned);
                org.junit.Assert
                        .assertEquals(
                                "return value oftestServiceMethod with three arguments yes string, 55555 and u User [firstName=First Name, profile=Profile [address=House No.1]]",
                                returned);
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
