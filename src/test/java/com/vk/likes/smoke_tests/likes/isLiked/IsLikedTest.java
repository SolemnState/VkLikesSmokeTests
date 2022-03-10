package com.vk.likes.smoke_tests.likes.isLiked;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ApiLikesReactionCanNotBeAppliedException;
import com.vk.api.sdk.exceptions.ApiPrivateProfileException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.base.BoolInt;
import com.vk.api.sdk.objects.likes.Type;
import com.vk.api.sdk.objects.likes.responses.IsLikedResponse;
import com.vk.likes.enums.ApiError;
import com.vk.likes.smoke_tests.BaseAutotest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class IsLikedTest extends BaseAutotest {

    private UserActor actor;
    private VkApiClient vk;
    private static final Logger LOG = LoggerFactory.getLogger(IsLikedTest.class);

    public IsLikedTest() {
        super();
        this.vk = super.getVkClient();
        this.actor = super.getUserActor();
    }

    @Test(description = "Положительный сценарий проверки нахождения объекта в списке \"Мне нравится\"",
            groups = {"positive"},
            dataProvider = "isLikedPositiveDataProvider",
            dataProviderClass = IsLikedDataProvider.class
    )
    public void isLikedPositiveTest(String name,
                                    Type type,
                                    int ownerId,
                                    int itemId,
                                    BoolInt isLiked,
                                    BoolInt isCopied) throws ClientException, ApiException {
        LOG.info("Проверка нахождения объекта с type = {} и itemId = {} у пользователя с ownerId = {} в списке", type, itemId, ownerId);
        IsLikedResponse actualResponse = vk.likes().isLiked(actor, type, itemId).ownerId(ownerId).execute();

        Assert.assertEquals(actualResponse.getLiked(), isLiked, "Результат для лайка не совпал с ожидаемым");
        Assert.assertEquals(actualResponse.getCopied(), isCopied, "Результат для репоста не совпал с ожидаемым");
    }

    @Test(description = "Проверка ошибок при проверке нахождения объекта в списке \"Мне нравится\"",
            groups = {"negative"},
            dataProvider = "isLikedErrorDataProvider",
            dataProviderClass = IsLikedDataProvider.class
    )
    public void isLikedNegativeTest(String name,
                                    Type type,
                                    int ownerId,
                                    int itemId,
                                    ApiError error) throws ClientException, ApiException {
        LOG.info("Попытка проверки нахождения объекта с type = {} и itemId = {} у пользователя с ownerId = {} в списке", type, itemId, ownerId);

        try {
            vk.likes().isLiked(actor, type, itemId).ownerId(ownerId).execute();
            fail();
        } catch (ApiPrivateProfileException exception) {
            LOG.info("Получено исключение {}", exception.toString());
            Assert.assertEquals(exception.getCode(), error.getCode(), "Код ошибки не совпадает с ожидаемым");
            Assert.assertEquals(exception.getDescription(), error.getDescription(), "Описание ошибки не совпадает с ожидаемым");
            Assert.assertEquals(exception.getMessage(), error.getErrorMessage(), "Сообщение об ошибке не совпадает с ожидаемым");
        }
    }
}
