package com.bohu.bigstock.neteaseTest;

import com.bohu.bigstock.finance.domain.HistoricalQuote;
import com.bohu.bigstock.finance.netease.HistQuotesRequest;
import com.bohu.bigstock.mock.MockedServer;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class NeteaseStockDailyDataTest extends MockedServer {

    @Test
    public void NormalDailyQuoteData() throws IOException {
        Calendar startDate=Calendar.getInstance();
        startDate.set(2018,Calendar.MARCH,18);

        Calendar endDate = (Calendar) startDate.clone();
        endDate.set(Calendar.MONTH,4);
        HistQuotesRequest histQuotesRequest = new HistQuotesRequest("600727",startDate,endDate);
        List<HistoricalQuote> historicalQuotesList = histQuotesRequest.getResult();
        assertTrue("数据应该返回41条记录",historicalQuotesList.size()==41);
    }
}
