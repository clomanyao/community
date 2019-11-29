package com.company.community.enums;

public enum NotificationTypeEnum {
    QUESTIONNOTICE(1,"回复了问题"),COMMENTNOTICE(2,"回复了评论");
    private int type;
    private String name;
    private  NotificationTypeEnum(int type,String name){
        this.type=type;
        this.name=name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
