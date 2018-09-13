package com.bohu.bigstock.dao.model;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;

@Table
public class Stock {

    @PrimaryKey
    private String symbol;
    private String name;
    private Date quoteOverallDataTillDate;
    private Date detailTradeDataTillDate;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getQuoteOverallDataTillDate() {
        return quoteOverallDataTillDate;
    }

    public void setQuoteOverallDataTillDate(Date quoteOverallDataTillDate) {
        this.quoteOverallDataTillDate = quoteOverallDataTillDate;
    }

    public Date getDetailTradeDataTillDate() {
        return detailTradeDataTillDate;
    }

    public void setDetailTradeDataTillDate(Date detailTradeDataTillDate) {
        this.detailTradeDataTillDate = detailTradeDataTillDate;
    }
}
