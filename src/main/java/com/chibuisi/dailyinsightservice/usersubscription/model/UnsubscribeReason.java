package com.chibuisi.dailyinsightservice.usersubscription.model;

import java.util.stream.Stream;

public enum UnsubscribeReason {
    UNKNOWN(0),
    EXPIRED_NO_PAY(1),
    EXPIRED_USER_CANCELLED(2),
    USER_CANCELLED(3);

    private int value;

    private UnsubscribeReason(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public Integer getValue(){
        return this.value;
    }
    public String getName(){
        return this.name();
    }

    public static UnsubscribeReason of(String name){
        return Stream.of(UnsubscribeReason.values())
                .filter(s -> s.getName().equalsIgnoreCase(name)).findFirst()
                .orElse(UnsubscribeReason.UNKNOWN);
    }
}
