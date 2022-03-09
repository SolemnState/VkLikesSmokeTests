package com.vk.likes.helpers;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.testng.AssertJUnit.fail;

public class UserActorHelper {
    private static UserActorHelper instance;
    private UserActor actor;
    Properties props = new Properties();
    String testsConfigPath = "src/test/resources/config.properties";
    private Integer appId;
    private String clientSecret;
    private String redirectUri;
    private String code;
    private static final Logger LOG = LoggerFactory.getLogger(UserActorHelper.class);

    private UserActorHelper(VkApiClient vk) throws ClientException, ApiException {
        try {
            LOG.error("Reading configuration properties from file {}" + testsConfigPath);
            props.load(new FileInputStream(testsConfigPath));
            appId = Integer.valueOf(props.getProperty("vk.appId"));
            clientSecret = props.getProperty("vk.clientSecret");
            redirectUri = props.getProperty("vk.redirectUri");
            code = props.getProperty("vk.code");
        } catch (IOException e) {
            LOG.error(e.getMessage());
            fail();
        }

        LOG.error("Creating new UserActor");
        UserAuthResponse authResponse = vk.oAuth().userAuthorizationCodeFlow(appId,
                clientSecret, redirectUri, code).execute();
        this.actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
    }

    public static UserActorHelper getInstance(VkApiClient vk) {
        if (instance == null) {
            try {
                instance = new UserActorHelper(vk);
            } catch (Exception ignore) {}
        }
        return instance;
    }

    public UserActor getActor() {
        return actor;
    }
}
