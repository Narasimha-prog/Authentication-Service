package com.lnr.authentication_service.user.domain.profile.vo;

public enum FieldLength {

    FIRST_NAME(2, 50),
    LAST_NAME(2, 50),
    EMAIL(5, 254),
    COUNTRY_CODE(3,5),
    PHONE_NUMBER(7, 15),
    ADDRESS(5, 250),
    PASSWORD(8, 128);

    private final int min;
    private final int max;

    FieldLength(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
