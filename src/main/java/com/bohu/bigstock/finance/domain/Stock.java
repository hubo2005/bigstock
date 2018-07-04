package com.bohu.bigstock.finance.domain;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.List;

import com.bohu.bigstock.finance.util.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Stijn Strickx
 */
public class Stock {

    private static final Logger log = LoggerFactory.getLogger(Stock.class);

    private final String symbol;
    private String name;

    private StockQuote quote;
    private List<HistoricalQuote> history;

    public Stock(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Checks if the returned name is null. This probably means that the symbol was not recognized by Yahoo Finance.
     * @return whether this stock's symbol is known by Yahoo Finance (true) or not (false)
     */
    public boolean isValid() {
        return this.name != null;
    }


    /**
     * Requests the historical quotes for this stock with the following characteristics.
     * <ul>
     * <li> from: specified value
     * <li> to: specified value
     * <li> interval: specified value
     * </ul>
     *
     * @param from          start date of the historical data
     * @param to            end date of the historical data
     * @param interval      the interval of the historical data
     * @return              a list of historical quotes from this stock
     * @throws java.io.IOException when there's a connection problem
     * @see                 #
     */
    public List<HistoricalQuote> getHistory(Calendar from, Calendar to, Interval interval) throws IOException {
       /* if(YahooFinance.HISTQUOTES2_ENABLED.equalsIgnoreCase("true")) {
            HistQuotes2Request hist = new HistQuotes2Request(this.symbol, from, to, interval);
            this.setHistory(hist.getResult());
        } else {
            HistQuotesRequest hist = new HistQuotesRequest(this.symbol, from, to, interval);
            this.setHistory(hist.getResult());
        }*/
        return this.history;
    }

    public void setHistory(List<HistoricalQuote> history) {
        this.history = history;
    }


    public String getSymbol() {
        return symbol;
    }

    /**
     * Get the full name of the stock
     *
     * @return the name or null if the data is not available
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.symbol + ": " ;
    }

    public void print() {
        System.out.println(this.symbol);
        System.out.println("--------------------------------");
        for (Field f : this.getClass().getDeclaredFields()) {
            try {
                System.out.println(f.getName() + ": " + f.get(this));
            } catch (IllegalArgumentException ex) {
                log.error(null, ex);
            } catch (IllegalAccessException ex) {
                log.error(null, ex);
            }
        }
        System.out.println("--------------------------------");
    }

}
