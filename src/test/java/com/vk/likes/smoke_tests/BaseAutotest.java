package com.vk.likes.smoke_tests;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.likes.helpers.UserActorHelper;
import org.testng.ITest;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;


public class BaseAutotest implements ITest {
    private VkApiClient vk;
    private UserActorHelper userActorHelper;
    private ThreadLocal<String> testName = new ThreadLocal<>();

    public BaseAutotest() {
        TransportClient transportClient = new HttpTransportClient();
        this.vk = new VkApiClient(transportClient);
        this.userActorHelper = UserActorHelper.getInstance(vk);
    }

    public VkApiClient getVkClient() {
        return vk;
    }

    public UserActor getUserActor() {
        return userActorHelper.getActor();
    }


    @BeforeMethod
    public void beforeMethod(Method method, Object[] testData){
        testName.set(method.getName() + "_" + testData[0]);
    }
    @Override
    public String getTestName() {
        return testName.get();
    }


}
