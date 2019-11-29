package com.company.community.enums;

public enum NotificationStatusEnum {
    NUREAD(0),READ(1);
    private int status;
    private NotificationStatusEnum(int status){
        this.status=status;
    }

    public int getStatus() {
        return status;
    }
}
