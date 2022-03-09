package com.vk.likes.smoke_tests.likes.add;

import com.vk.api.sdk.objects.likes.Type;
import com.vk.likes.enums.ApiError;
import org.testng.annotations.DataProvider;

public class AddLikesDataProvider {

    @DataProvider(name = "addLikesPositiveDataProvider")
    public static Object[][] addLikesPositiveDataProvider() {
        return new Object[][]{
                {
                    "Пост",
                    Type.POST,
                    3038
                },
                {
                    "Комментарий",
                    Type.COMMENT,
                    3040
                },
                {
                    "Видео",
                    Type.VIDEO,
                    456239631
                },
                {
                    "Комментарий к видео",
                    Type.VIDEO_COMMENT,
                    2
                },
                {
                    "Фото",
                    Type.PHOTO,
                    457250998
                },
                {
                    "Комментарий к фото",
                    Type.PHOTO_COMMENT,
                    294
                }
        };
    }

    @DataProvider(name = "addLikesErrorDataProvider")
    public static Object[][] addLikesErrorDataProvider() {
        return new Object[][] {
                {
                    "Приватный профиль",
                    Type.POST,
                    6959674,
                    2624,
                    ApiError.PRIVATE_PROFILE
                },
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
