package com.vk.likes.smoke_tests.likes.delete;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ApiPrivateProfileException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.likes.Type;
import com.vk.api.sdk.objects.likes.responses.DeleteResponse;
import com.vk.api.sdk.objects.likes.responses.GetListResponse;
import com.vk.likes.enums.ApiError;
import com.vk.likes.smoke_tests.BaseAutotest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


import static org.testng.Assert.fail;

public class DeleteLikesTest extends BaseAutotest {

    private UserActor actor;
    private VkApiClient vk;
    private static final Logger LOG = LoggerFactory.getLogger(DeleteLikesTest.class);

    public DeleteLikesTest() {
        super();
        this.vk = super.getVkClient();
        this.actor = super.getUserActor();
    }

    @Test(description = "Удаление лайка без указания owner_id",
            groups = {"positive"},
            dataProvider = "deleteLikesPositiveDataProvider",
            dataProviderClass = DeleteLikesDataProvider.class)
    public void deleteLikePositiveTest(String name, Type type, int itemId) throws ClientException, ApiException {
        LOG.info("Добавление лайка на объект с type = {} и itemId = {} текущему пользователю", type, itemId);
        vk.likes().add(actor, type, itemId).execute();

        LOG.info("Получение списка лайкнувших пользователей с type = {} и itemId = {}", type, itemId);
        GetListResponse getListResponse = vk.likes().getList(actor, type).itemId(itemId).execute();
        int likesCountBefore = getListResponse.getCount();
        LOG.info("Количество лайков до удаления = " + likesCountBefore);

        LOG.info("Удаление лайка на объекте с type = {} и itemId = {} у текущего пользователя", type, itemId);
        DeleteResponse deleteResponse = vk.likes().delete(actor, type, itemId).execute();
        LOG.info(deleteResponse.toPrettyString());

        int actualLikesCount = deleteResponse.getLikes();
        Assert.assertEquals(actualLikesCount, likesCountBefore - 1, "Лайк не был удален");
    }

    @Test(description = "Проверка ошибок при удалении лайка",
            groups = {"negative"},
            dataProvider = "deleteLikesErrorDataProvider",
            dataProviderClass = DeleteLikesDataProvider.class
    )
    public void deleteLikesNegativeTest(String name,
                                        Type type,
                                        int ownerId,
                                        int itemId,
                                        ApiError error) throws ClientException, ApiException {
        LOG.info("Попытка удаления лайка на объекте с type = {} и itemId = {} у текущего пользователя", type, itemId);
        try {
            vk.likes().delete(actor, type, itemId).ownerId(ownerId).execute();
            fail();
        } catch (ApiPrivateProfileException exception) {
            LOG.info("Получено исключение {}", exception.toString());
            Assert.assertEquals(exception.getCode(), error.getCode(), "Код ошибки не совпадает с ожидаемым");
            Assert.assertEquals(exception.getDescription(), error.getDescription(), "Описание ошибки не совпадает с ожидаемым");
            Assert.assertEquals(exception.getMessage(), error.getErrorMessage(), "Сообщение об ошибке не совпадает с ожидаемым");
        }
    }
}
