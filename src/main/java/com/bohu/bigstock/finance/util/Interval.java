package com.bohu.bigstock.finance.util;

public enum Interval {

    DAILY("d"),
    WEEKLY("w"),
    MONTHLY("m");

    private final String tag;

    Interval(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }
}
