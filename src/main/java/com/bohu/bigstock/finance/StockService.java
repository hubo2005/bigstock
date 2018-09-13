package com.bohu.bigstock.finance;

import com.bohu.bigstock.finance.JQK.JQKStockCollection;
import com.bohu.bigstock.finance.domain.HistoricalQuote;
import com.bohu.bigstock.finance.domain.HistoricalTradeDetail;
import com.bohu.bigstock.finance.domain.Stock;
import com.bohu.bigstock.finance.netease.HistQuotesRequest;
import com.bohu.bigstock.finance.sina.HistTradeDetailRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@Component
public class StockService {

    public static List<HistoricalQuote> getHistoricalQuoteByDay(String symbol, Calendar startDate, Calendar endDate ){
        HistQuotesRequest histQuotesRequest = new HistQuotesRequest(symbol,startDate,endDate);
        try {
            List<HistoricalQuote> historicalQuotesList = histQuotesRequest.getResult();
            for (HistoricalQuote historicalQuote: historicalQuotesList) {
                System.out.println(historicalQuote.getVolume());
                System.out.println(historicalQuote.getClose());
                System.out.println(historicalQuote.getOpen() + historicalQuote.getSymbol());
                System.out.println("成交额："+historicalQuote.getTradeAmount() );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<HistoricalTradeDetail> getStockTradeDetailsByDay(String symbol,Calendar tradeDate) {
        HistTradeDetailRequest histTradeDetailRequest = new HistTradeDetailRequest();
        try {
            List<HistoricalTradeDetail> historicalTradeDetailsList = histTradeDetailRequest.getResult(symbol,tradeDate);
            for (HistoricalTradeDetail historicalTradeDetail: historicalTradeDetailsList) {
                System.out.println(historicalTradeDetail.getSymbol());
                System.out.println(historicalTradeDetail.getDealPrice());
                System.out.println(historicalTradeDetail.getVibration());
                System.out.println(historicalTradeDetail.getVolume());
                System.out.println(historicalTradeDetail.getTradeAmount());



            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static List<Stock> loadStockNameAndSymbolFromResource(String filePath) {
        return new JQKStockCollection().loadStockSymbolAndNameFromResourceFile(filePath);
    }


}
