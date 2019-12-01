package com.company.community.enums;

public enum LikeCountEnum {
    LIKECOUNT(200,1),
    LIKESTATUS(300,1);
    private Integer code;
    private Integer likeProperty;

    LikeCountEnum(Integer code, Integer likeProperty) {
        this.code = code;
        this.likeProperty = likeProperty;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getLikeProperty() {
        return likeProperty;
    }
}
