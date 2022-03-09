package com.vk.likes.smoke_tests.likes.add;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.*;
import com.vk.api.sdk.objects.likes.Type;
import com.vk.api.sdk.objects.likes.responses.AddResponse;
import com.vk.api.sdk.objects.likes.responses.GetListResponse;
import com.vk.likes.enums.ApiError;
import com.vk.likes.smoke_tests.BaseAutotest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


import static org.testng.Assert.fail;

public class AddLikesTest extends BaseAutotest {

    private UserActor actor;
    private VkApiClient vk;
    private static final Logger LOG = LoggerFactory.getLogger(AddLikesTest.class);

    public AddLikesTest() {
        super();
        this.vk = super.getVkClient();
        this.actor = super.getUserActor();
    }

    @Test(description = "Добавление лайка без указания owner_id",
            groups = {"positive"},
            dataProvider = "addLikesPositiveDataProvider",
            dataProviderClass = AddLikesDataProvider.class)
    public void addLikePositiveTest(String name, Type type, int itemId) throws ClientException, ApiException {
        LOG.info("Получение списка лайкнувших пользователей с type = {} и itemId = {}", type, itemId);
        GetListResponse getListResponse = vk.likes().getList(actor, type).itemId(itemId).execute();
        int likesCountBefore = getListResponse.getCount();
        LOG.info("Количество лайков до добавления = ", likesCountBefore);

        LOG.info("Добавление лайка на объект с type = {} и itemId = {} текущему пользователю", type, itemId);
        AddResponse response = vk.likes().add(actor, type, itemId).execute();

        int actualLikesCount = response.getLikes();
        LOG.info("Количество лайков после добавления = {}", actualLikesCount);

        try {
            Assert.assertEquals(actualLikesCount, likesCountBefore + 1, "Лайк не был добавлен");
        } finally {
            LOG.info("Удаление лайка на объекте с type = {} и itemId = {} у текущего пользователя", type, itemId);
            vk.likes().delete(actor, type, itemId).execute();
        }
    }

    @Test(description = "Проверка ошибок при добавлении лайка",
            groups = {"negative"},
            dataProvider = "addLikesErrorDataProvider",
            dataProviderClass = AddLikesDataProvider.class
    )
    public void addLikesNegativeTest(String name,
                                     Type type,
                                     int ownerId,
                                     int itemId,
                                     ApiError error) throws ClientException, ApiException {
        LOG.info("Попытка добавления лайка на объекте с type = {} и itemId = {} у текущего пользователя", type, itemId);
        try {
            vk.likes().add(actor, type, itemId).ownerId(ownerId).execute();
            fail();
        } catch (ApiPrivateProfileException | ApiLikesReactionCanNotBeAppliedException exception) {
            LOG.info("Получено исключение {}", exception.toString());
            Assert.assertEquals(exception.getCode(), error.getCode(), "Код ошибки не совпадает с ожидаемым");
            Assert.assertEquals(exception.getDescription(), error.getDescription(), "Описание ошибки не совпадает с ожидаемым");
            Assert.assertEquals(exception.getMessage(), error.getErrorMessage(), "Сообщение об ошибке не совпадает с ожидаемым");
        }
    }
}
