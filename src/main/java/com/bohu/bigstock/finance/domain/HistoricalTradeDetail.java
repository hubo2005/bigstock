package com.bohu.bigstock.finance.domain;

import java.math.BigDecimal;
import java.util.Calendar;

public class HistoricalTradeDetail {
    // 股票代号
    private String symbol;

    //成交时间 - 年月日时分秒
    private Calendar tradeDateTime;

    //成交价格
    private BigDecimal dealPrice;

    //价格变动
    private BigDecimal vibration;

    private Long volume;
    //单位 元
    private BigDecimal tradeAmount;

    //1:买盘 ,0:卖盘
    private int tradeType;


    public HistoricalTradeDetail(String symbol, Calendar tradeDateTime, BigDecimal dealPrice, BigDecimal vibration, Long volume, BigDecimal tradeAmount, int tradeType) {
        this.setSymbol(symbol);
        this.setTradeDateTime(tradeDateTime);
        this.setDealPrice(dealPrice);
        this.setVibration(vibration);
        this.setVolume(volume);
        this.setTradeAmount(tradeAmount);
        this.setTradeType(tradeType);
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Calendar getTradeDateTime() {
        return tradeDateTime;
    }

    public void setTradeDateTime(Calendar tradeDateTime) {
        this.tradeDateTime = tradeDateTime;
    }

    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
    }

    public BigDecimal getVibration() {
        return vibration;
    }

    public void setVibration(BigDecimal vibration) {
        this.vibration = vibration;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }
}
