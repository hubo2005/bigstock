package com.bohu.bigstock.finance.netease;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bohu.bigstock.finance.domain.HistoricalQuote;
import com.bohu.bigstock.finance.util.Interval;
import com.bohu.bigstock.finance.util.RedirectableRequest;
import com.bohu.bigstock.finance.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class HistQuotesRequest {

    private static final Logger log = LoggerFactory.getLogger(HistQuotesRequest.class);

    public static final String NETEASE_QUOTES_BASE_URL = System.getProperty("netease.baseurl.quotes", "http://quotes.money.163.com/service/chddata.html");
    public static final String QUOTES_CSV_DELIMITER = ",";
    public static final int CONNECTION_TIMEOUT = 20000;
    private final String symbol;

    private final Calendar from;
    private final Calendar to;

    private final Interval interval;

    public static final Calendar DEFAULT_FROM = Calendar.getInstance();

    static {
        DEFAULT_FROM.add(Calendar.YEAR, -1);
    }
    public static final Calendar DEFAULT_TO = Calendar.getInstance();
    public static final Interval DEFAULT_INTERVAL = Interval.DAILY;

    public HistQuotesRequest(String symbol) {
        this(symbol, DEFAULT_INTERVAL);
    }

    public HistQuotesRequest(String symbol, Interval interval) {
        this(symbol, DEFAULT_FROM, DEFAULT_TO, interval);
    }

    public HistQuotesRequest(String symbol, Calendar from, Calendar to) {
        this(symbol, from, to, DEFAULT_INTERVAL);
    }

    public HistQuotesRequest(String symbol, Calendar from, Calendar to, Interval interval) {
        this.symbol = symbol;
        this.from = this.cleanHistCalendar(from);
        this.to = this.cleanHistCalendar(to);
        this.interval = interval;
    }

    public HistQuotesRequest(String symbol, Date from, Date to) {
        this(symbol, from, to, DEFAULT_INTERVAL);
    }

    public HistQuotesRequest(String symbol, Date from, Date to, Interval interval) {
        this(symbol, interval);
        this.from.setTime(from);
        this.to.setTime(to);
        this.cleanHistCalendar(this.from);
        this.cleanHistCalendar(this.to);
    }

    /**
     * Put everything smaller than days at 0
     * @param cal calendar to be cleaned
     */
    private Calendar cleanHistCalendar(Calendar cal) {
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        return cal;
    }

    public List<HistoricalQuote> getResult() throws IOException {

        List<HistoricalQuote> result = new ArrayList<HistoricalQuote>();

        if(this.from.after(this.to)) {
            log.warn("Unable to retrieve historical quotes. "
                    + "From-date should not be after to-date. From: "
                    + this.from.getTime() + ", to: " + this.to.getTime());
            return result;
        }

        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("code", getNetEaseStockSymbol(this.symbol));

        params.put("start", Utils.calendarToDateString(this.from,"yyyyMMdd"));
        params.put("end", Utils.calendarToDateString(this.to,"yyyyMMdd"));
        params.put("fields", "TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;VOTURNOVER;VATURNOVER");

        String url = NETEASE_QUOTES_BASE_URL + "?" + Utils.getURLParameters(params);

        // Get CSV from netease
        log.info("Sending request: " + url);

        URL request = new URL(url);
        RedirectableRequest redirectableRequest = new RedirectableRequest(request, 5);
        redirectableRequest.setConnectTimeout(CONNECTION_TIMEOUT);
        redirectableRequest.setReadTimeout(CONNECTION_TIMEOUT);
        URLConnection connection = redirectableRequest.openConnection();

        InputStreamReader is = new InputStreamReader(connection.getInputStream());
        BufferedReader br = new BufferedReader(is);
        br.readLine(); // skip the first line
        // Parse CSV
        for (String line = br.readLine(); line != null; line = br.readLine()) {

            log.info("Parsing CSV line: " + Utils.unescape(line));
            HistoricalQuote quote = this.parseCSVLine(line);
            result.add(quote);
        }
        return result;
    }

    private HistoricalQuote parseCSVLine(String line) {
        String[] data = line.split(QUOTES_CSV_DELIMITER);
        return new HistoricalQuote(this.symbol,
                Utils.parseHistDate(data[0]),
                Utils.getBigDecimal(data[6]),
                Utils.getBigDecimal(data[5]),
                Utils.getBigDecimal(data[4]),
                Utils.getBigDecimal(data[3]),
                Utils.getBigDecimal(data[3]),
                Utils.getLong(data[10]),
                Utils.getBigDecimal(data[11])
        );
    }


    private static String getNetEaseStockSymbol(String symbol) {
        Assert.notNull(symbol,"股票代码不能为空！");
        Assert.isTrue(symbol.length()==6,"股票代码应该是6位，输入值为"+symbol);
        if (symbol.startsWith("6")) return "0"+symbol;
        return "1"+symbol;
    }

}
