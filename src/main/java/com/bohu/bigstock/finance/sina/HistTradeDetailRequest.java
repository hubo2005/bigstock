package com.bohu.bigstock.finance.sina;

import com.bohu.bigstock.finance.domain.HistoricalTradeDetail;
import com.bohu.bigstock.finance.util.RedirectableRequest;
import com.bohu.bigstock.finance.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

public class HistTradeDetailRequest {
    private static final Logger log = LoggerFactory.getLogger(HistTradeDetailRequest.class);

    public static final String SINA_QUOTES_BASE_URL = "http://market.finance.sina.com.cn/downxls.php";
    //?date=[日期]&symbol=[市场][股票代码]
    public static final String QUOTES_CSV_DELIMITER = "\t";
    public static final int CONNECTION_TIMEOUT = 20000;


    public List<HistoricalTradeDetail> getResult(String symbol, Calendar tradeDate) throws IOException {
        Assert.notNull(symbol,"股票代号不能为空");
        Assert.notNull(tradeDate,"查询日期不能为空");
        Assert.notNull(tradeDate.get(Calendar.YEAR),"查询日期年份不能为空");
        Assert.notNull(tradeDate.get(Calendar.MONTH),"查询日期月份不能为空");
        Assert.notNull(tradeDate.get(Calendar.DAY_OF_MONTH),"查询日期不能为空");

        String dateStr = Utils.calendarToDateString(tradeDate, "yyyy-MM-dd");
        String sinaStockCode = getSinaStockCodeFormat(symbol);
        Map<String, String> params = new LinkedHashMap<String, String>(2);
        params.put("date", dateStr);
        params.put("symbol", sinaStockCode);
        List<HistoricalTradeDetail> result = new ArrayList<HistoricalTradeDetail>(2);

        String url = this.SINA_QUOTES_BASE_URL+"?" + Utils.getURLParameters(params);
        // get xls from Sina
        log.info("sending request to ："+url);
        URL request = new URL(url);
        RedirectableRequest redirectableRequest = new RedirectableRequest(request,3);
        redirectableRequest.setConnectTimeout(CONNECTION_TIMEOUT);
        redirectableRequest.setReadTimeout(CONNECTION_TIMEOUT);
        HttpURLConnection connection = (HttpURLConnection)redirectableRequest.openConnection();

        int responseCode = connection.getResponseCode();
        //always check HTTP response code first
        if(responseCode == HttpURLConnection.HTTP_OK) {
            String contentType = connection.getContentType();
            String disposition = connection.getHeaderField("Content-Disposition");
            int contentLength = connection.getContentLength();

            if (!contentType.equalsIgnoreCase("application/vnd.ms-excel")) {
                log.warn("查询结果不是excel格式,查询股票代码:"+symbol+" 查询日期："+ Utils.calendarToDateString(tradeDate,"yyyy-MM-dd"));
                return result;
            }

        }
        InputStreamReader is = new InputStreamReader(connection.getInputStream());
        BufferedReader br = new BufferedReader(is);
        br.readLine(); // skip the first line
        // Parse CSV
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            log.info("Parsing CSV line: " + Utils.unescape(line));
            HistoricalTradeDetail tradeDetail = this.parseXLSLine(symbol,tradeDate,line);
            if(tradeDetail!=null)   result.add(tradeDetail);
        }
        log.info("come to the right place");
        return result;
    }

    private HistoricalTradeDetail parseXLSLine(String symbol, Calendar tradeDate, String line) {
        String[] data = line.split(QUOTES_CSV_DELIMITER);
        if ("0".equalsIgnoreCase(data[3]) && "0".equalsIgnoreCase(data[4])) return null;

        Calendar tradeDT = (Calendar) tradeDate.clone();
        String tradeDateStr = Utils.calendarToDateString(tradeDT,"yyyy-MM-dd") + " "+data[0];
        HistoricalTradeDetail historicalTradeDetail = new HistoricalTradeDetail(
                symbol,
                Utils.parseDateStringToCalendar(tradeDateStr,"yyyy-MM-dd HH:mm:ss"),
                Utils.getBigDecimal(data[1]),
                Utils.getSinaPriceViberation(data[2]),
                Utils.getLong(data[3]),
                Utils.getBigDecimal(data[4]),
                1
                );


        return historicalTradeDetail;
    }

    private String getSinaStockCodeFormat(String symbol) {
        if (symbol.startsWith("6")) return "sh" + symbol;
        return "sz"+symbol;
    }



}
