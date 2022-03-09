package com.vk.likes.smoke_tests.likes.delete;

import com.vk.api.sdk.objects.likes.Type;
import com.vk.likes.enums.ApiError;
import org.testng.annotations.DataProvider;

public class DeleteLikesDataProvider {

    @DataProvider(name = "deleteLikesPositiveDataProvider")
    public static Object[][] deleteLikesPositiveDataProvider() {
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

    @DataProvider(name = "deleteLikesErrorDataProvider")
    public static Object[][] deleteLikesErrorDataProvider() {
        return new Object[][]{
                {
                        "Приватный профиль",
                        Type.POST,
                        6959674,
                        2624,
                        ApiError.PRIVATE_PROFILE
                }
        };
    }
}
