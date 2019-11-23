package com.company.community.enums;

public enum CommentEnumType {
    QUESTION(1), COMMENT(2),COMMENTOK(200);

    private Integer type;

    private CommentEnumType(Integer type){
        this.type=type;
    }

    public Integer getType() {
        return type;
    }
}
