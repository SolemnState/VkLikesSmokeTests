package com.vk.likes.smoke_tests.likes.isLiked;

import com.vk.api.sdk.objects.base.BoolInt;
import com.vk.api.sdk.objects.likes.Type;
import com.vk.likes.enums.ApiError;
import org.testng.annotations.DataProvider;

public class IsLikedDataProvider {

    @DataProvider(name = "isLikedPositiveDataProvider")
    public static Object[][] isLikedPositiveDataProvider() {
        return new Object[][]{
                {
                        "Пост",
                        Type.POST,
                        56635423,
                        4747,
                        BoolInt.YES,
                        BoolInt.NO
                },
                {
                        "Комментарий",
                        Type.COMMENT,
                        56635423,
                        4748,
                        BoolInt.YES,
                        BoolInt.NO
                },
                {
                        "Видео",
                        Type.VIDEO,
                        32414827,
                        456239631,
                        BoolInt.NO,
                        BoolInt.NO
                },
                {
                        "Комментарий к видео",
                        Type.VIDEO_COMMENT,
                        32414827,
                        2,
                        BoolInt.NO,
                        BoolInt.NO
                },
                {
                        "Фото",
                        Type.PHOTO,
                        -197601594,
                        457239022,
                        BoolInt.YES,
                        BoolInt.YES
                },
                {
                        "Комментарий к фото",
                        Type.PHOTO_COMMENT,
                        32414827,
                        294,
                        BoolInt.NO,
                        BoolInt.NO
                }
        };
    }

    @DataProvider(name = "isLikedErrorDataProvider")
    public static Object[][] isLikedErrorDataProvider() {
        return new Object[][] {
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
