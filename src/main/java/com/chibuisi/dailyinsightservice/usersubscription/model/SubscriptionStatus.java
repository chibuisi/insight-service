package com.chibuisi.dailyinsightservice.usersubscription.model;

import java.util.stream.Stream;

public enum SubscriptionStatus {
    INACTIVE(0),
    ACTIVE(1),
    ACTIVE_PAID(2),
    INACTIVE_NO_PAYMENT(3),
    UNKNOWN(4),
    DEACTIVATED(5),
    CANCELLED(6);

    private int value;

    private SubscriptionStatus(int value){
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

    public static SubscriptionStatus of(String name){
        return Stream.of(SubscriptionStatus.values())
                .filter(s -> s.getName().equalsIgnoreCase(name)).findFirst()
                .orElse(SubscriptionStatus.UNKNOWN);
    }
}
