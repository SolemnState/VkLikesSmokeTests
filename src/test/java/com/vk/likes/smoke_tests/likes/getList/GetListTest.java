package com.vk.likes.smoke_tests.likes.getList;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ApiLikesReactionCanNotBeAppliedException;
import com.vk.api.sdk.exceptions.ApiPrivateProfileException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.likes.Type;
import com.vk.api.sdk.objects.likes.responses.GetListResponse;
import com.vk.api.sdk.queries.likes.LikesGetListQuery;
import com.vk.likes.enums.ApiError;
import com.vk.likes.smoke_tests.BaseAutotest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.fail;

public class GetListTest extends BaseAutotest {

    private UserActor actor;
    private VkApiClient vk;
    private static final Logger LOG = LoggerFactory.getLogger(GetListTest.class);

    public GetListTest() {
        super();
        this.vk = super.getVkClient();
        this.actor = super.getUserActor();
    }

    @Test(description = "Положительный сценарий проверки списка \"Мне нравится\"",
            groups = {"positive"},
            dataProvider = "getListPositiveDataProvider",
            dataProviderClass = GetListDataProvider.class

    )
    public void getListPositiveTest(String name,
                                    Type type,
                                    Map<String, Object> parameters,
                                    Map<String, Object> expectedResponse) throws ClientException, ApiException {

        LOG.info("Создание запроса на получение списка лайкнувших пользователей с параметрами");
        LikesGetListQuery query = vk.likes().getList(actor, type);
        parameters.forEach((k, v) -> query.unsafeParam(k, v));

        LOG.info("Получение списка лайкнувших пользователей с type = {} и доп. параметрами {}", type, parameters);
        GetListResponse actualResponse = query.execute();
        LOG.info("Список лайкнувших пользователей: " + actualResponse.toPrettyString());

        Assert.assertEquals(
                actualResponse.getItems(),
                expectedResponse.get("items"),
                "Полученный список идентификаторов пользователей не совпадает с ожидаемым");

    }

    @Test(description = "Негативный сценарий проверки списка \"Мне нравится\"",
            groups = {"negative"},
            dataProvider = "getListErrorDataProvider",
            dataProviderClass = GetListDataProvider.class
    )
    public void getListNegativeTest(String name,
                                    Type type,
                                    int ownerId,
                                    int itemId,
                                    ApiError error) throws ClientException, ApiException {
        LOG.info("Попытка получения списка лайков на объекте с type = {} и itemId = {} у пользователя с ownerId = {}", type, itemId, ownerId);

        try {
            vk.likes().getList(actor, type).ownerId(ownerId).itemId(itemId).execute();
            fail();
        } catch (ApiLikesReactionCanNotBeAppliedException exception) {
            LOG.info("Получено исключение {}", exception.toString());
            Assert.assertEquals(exception.getCode(), error.getCode(), "Код ошибки не совпадает с ожидаемым");
            Assert.assertEquals(exception.getDescription(), error.getDescription(), "Описание ошибки не совпадает с ожидаемым");
            Assert.assertEquals(exception.getMessage(), error.getErrorMessage(), "Сообщение об ошибке не совпадает с ожидаемым");
        }
    }
}
