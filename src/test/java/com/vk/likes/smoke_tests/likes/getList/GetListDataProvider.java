package com.vk.likes.smoke_tests.likes.getList;

import com.vk.api.sdk.objects.likes.Type;
import com.vk.likes.enums.ApiError;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class GetListDataProvider {

    @DataProvider(name = "getListPositiveDataProvider")
    public static Object[][] getListPositiveDataProvider() {
        return new Object[][]{
                {
                        "Пост",
                        Type.POST,
                        Map.ofEntries(
                                Map.entry("owner_id", 56635423),
                                Map.entry("item_id", 4747),
                                Map.entry("skip_own", true)
                        ),
                        Map.ofEntries(
                                Map.entry("count", 2),
                                Map.entry("items", Arrays.asList(2060668, 87814099))
                        )
                },
                {
                        "Комментарий",
                        Type.COMMENT,
                        Map.ofEntries(
                                Map.entry("owner_id", 56635423),
                                Map.entry("item_id", 4748),
                                Map.entry("filter", "likes")
                        ),
                        Map.ofEntries(
                                Map.entry("count", 2),
                                Map.entry("items", Arrays.asList(32414827, 56635423))
                        )
                },
                {
                        "Видео",
                        Type.VIDEO,
                        Map.ofEntries(
                                Map.entry("owner_id", 32414827),
                                Map.entry("item_id", 456239631),
                                Map.entry("filter", "copies")
                        ),
                        Map.ofEntries(
                                Map.entry("count", 0),
                                Map.entry("items", new ArrayList<Integer>())
                        )
                },
                {
                        "Комментарий к видео",
                        Type.VIDEO_COMMENT,
                        Map.ofEntries(
                                Map.entry("owner_id", -203135117),
                                Map.entry("item_id", 1),
                                Map.entry("offset", 3)
                        ),
                        Map.ofEntries(
                                Map.entry("count", 6),
                                Map.entry("items", Arrays.asList(376214054, 435611536, 507954867))
                        )
                },
                {
                        "Фото",
                        Type.PHOTO,
                        Map.ofEntries(
                                Map.entry("owner_id", 32414827),
                                Map.entry("item_id", 457250998),
                                Map.entry("friend_only", 1)
                        ),
                        Map.ofEntries(
                                Map.entry("count", 6),
                                Map.entry("items", Arrays.asList(39300833, 233829680, 101605656))
                        )
                },
                {
                        "Комментарий к фото",
                        Type.PHOTO_COMMENT,
                        Map.ofEntries(
                                Map.entry("owner_id", -203135117),
                                Map.entry("item_id", 9335),
                                Map.entry("count", 5)
                        ),
                        Map.ofEntries(
                                Map.entry("count", 7),
                                Map.entry("items", Arrays.asList(544773149, 579911439, 626639563, 289436836, 521913635))
                        )
                }
        };
    }

    @DataProvider(name = "getListErrorDataProvider")
    public static Object[][] getListErrorDataProvider() {
        return new Object[][]{
                {
                        "Неподдерживаемый тип объекта",
                        Type.TEXTPOST,
                        -57902527,
                        87297,
                        ApiError.CANT_APPLY_REACTION
                }
        };
    }
}
