package com.vk.likes.enums;

public enum ApiError {
    PRIVATE_PROFILE(30, "This profile is private", "This profile is private"),
    CANT_APPLY_REACTION(232, "Reaction can not be applied to the object", "Reaction can not be applied to the object");

    private Integer code;
    private String message;
    private String description;

    ApiError(Integer code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public String getErrorMessage() {
        return this.description + " (" + this.code + "): " + this.message;
    }

}
