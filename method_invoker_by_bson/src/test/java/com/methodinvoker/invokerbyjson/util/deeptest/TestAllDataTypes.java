/*
 * Copyright [2014] [web firm]
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
 * @author Visruth CV
 */
package com.methodinvoker.invokerbyjson.util.deeptest;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.methodinvoker.invokerbybson.data.InputData;
import com.methodinvoker.invokerbybson.data.Result;
import com.methodinvoker.invokerbyjson.util.InvokerBsonUtil;
import com.methodinvoker.invokerbyjson.util.JsonBsonUtil;

/**
 * 
 * @author Visruth CV
 * @since 1.0.0
 */
public class TestAllDataTypes {
    
    @Test
    public void convertJsonToBsonAndTestMethodInvocation() throws Exception {
        
        org.codehaus.jackson.map.ObjectMapper jsonMapper = new org.codehaus.jackson.map.ObjectMapper();
        
        InputData inputData = new InputData();
        
        inputData.setMethodClass(TestService.class);
        inputData.setMethodName("testMethod");
        
        inputData.setMethodArgumentTypes(java.sql.Timestamp.class,
                Integer.TYPE, String.class, Float.TYPE, Double.TYPE,
                Boolean.TYPE, Character.TYPE, Short.TYPE, Long.TYPE, Byte.TYPE,
                TestBean.class);
        short s = 142;
        byte b = 88;
        TestBean testBean = new TestBean();
        testBean.setBooleanValue(true);
        testBean.setByteValue((byte)56);
        testBean.setCharValue('v');
        testBean.setDoubleValue(4555D);
        testBean.setFloatValue(654F);
        testBean.setIntValue(434);
        testBean.setLongValue(3434L);
        testBean.setShortValue((short)55);
        testBean.setStringValue("this is a string property");
        
        inputData.setMethodJsonArguments(55, 150, "some string", 555f, 5252D, true, 'a', s, 5698L, b, testBean);
        
        try {
            jsonMapper.writeValue(new File("C:\\Users\\user\\Desktop\\temp\\sample_json_bson\\SampleJson.txt"), inputData);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            fail("failed jsonMapper.writeValue");
        }
        
        try (
                FileInputStream fisJson = new FileInputStream(
                        "C:\\Users\\user\\Desktop\\temp\\sample_json_bson\\SampleJson.txt");
                
                FileOutputStream fosBson = new FileOutputStream(
                        "C:\\Users\\user\\Desktop\\temp\\sample_json_bson\\SampleBson.txt");
                FileInputStream fisBson = new FileInputStream(
                        "C:\\Users\\user\\Desktop\\temp\\sample_json_bson\\SampleBson.txt");
                ) {

            java.nio.ByteBuffer byteBuffer = ByteBuffer.allocate(fisJson
                    .available());
            fisJson.getChannel().read(byteBuffer);
            byte[] convertJsonToBson = JsonBsonUtil
            .convertJsonToBson(byteBuffer.array());
            ByteBuffer buffer = ByteBuffer.allocate(convertJsonToBson.length);
            buffer.clear();
            buffer.put(convertJsonToBson);
            buffer.flip();
            FileChannel channel = fosBson. getChannel();
            channel.write(buffer);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = 0;
            byte[] bytes = new byte[1024];
            while((read = fisBson.read(bytes)) != -1) {
                baos.write(bytes, 0, read);
            }
            
            try {
                Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
                map.put(TestService.class, new TestServiceImpl());
                byte[] invokeAndReturnBson = InvokerBsonUtil.invokeAndReturnBson(map, baos.toByteArray());
                Result invokeAndReturnResult = InvokerBsonUtil.invokeAndReturnResult(map, baos.toByteArray());
                
            } catch (NoSuchMethodException | IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                
                fail("NoSuchMethodException | IllegalArgumentException");
            }
            
            byte[] convertBsonToJson = JsonBsonUtil.convertBsonToJson(baos.toByteArray());
            System.out.println(new String(convertBsonToJson));
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("failed FileNotFoundException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("failed IOException");
        }

    
    }


}
