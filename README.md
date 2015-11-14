# method_invoker_by_data
This project provides utility to invoke methods in an object by the given json data.

Sample code :

String jsonString contains :-

{
    "methodName": "testServiceMethod",
    "methodArgumentTypes": [
        "java.lang.String",
        "java.lang.Integer",
        "com.methodinvoker.invokerbyjson.data.User"
    ],
    "methodJsonArguments": [
        "yes string",
        55555,
        "{\"firstName\":\"First Name\",\"profile\":{\"address\":\"House No.1\"}}"
    ],
    "methodClass": "com.methodinvoker.invokerbyjson.util.TestService"
}

Map<Class<?>, Object> map = new ConcurrentHashMap<Class<?>, Object>();
TestService testService = new TestService();
map.put(TestService.class, testService);

// the following will invoke the method testServiceMethod(String, Integer, com.methodinvoker.invokerbyjson.data.User) from the given object testService.

Object returned = InvokerUtil.invoke(map, jsonString); 

