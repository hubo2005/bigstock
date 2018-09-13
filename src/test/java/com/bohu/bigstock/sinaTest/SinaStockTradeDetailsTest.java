package com.bohu.bigstock.sinaTest;

import com.bohu.bigstock.finance.domain.HistoricalTradeDetail;
import com.bohu.bigstock.finance.sina.HistTradeDetailRequest;
import com.bohu.bigstock.mock.MockedServer;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import static org.junit.Assert.*;

public class SinaStockTradeDetailsTest extends MockedServer{

    @Test
    public void normalXLSDownloadTest() throws IOException {
        Calendar c1=Calendar.getInstance();
        c1.set(2018,Calendar.APRIL,18);
        HistTradeDetailRequest histTradeDetailRequest = new HistTradeDetailRequest();
        List<HistoricalTradeDetail> historicalTradeDetailsList = histTradeDetailRequest.getResult("600727",c1);
        assertTrue(historicalTradeDetailsList.size()>0);
        assertEquals(new BigDecimal("9.65"),historicalTradeDetailsList.get(0).getDealPrice());
        assertEquals("600727",historicalTradeDetailsList.get(0).getSymbol());

    }

    @Test
    public void noDataReturnTest() throws IOException {
        Calendar c1 = Calendar.getInstance();
        c1.set(2018,2,18); // this is not a working date
        HistTradeDetailRequest histTradeDetailRequest = new HistTradeDetailRequest();
        List<HistoricalTradeDetail> historicalTradeDetailsList = histTradeDetailRequest.getResult("600727",c1);
        assertTrue(historicalTradeDetailsList.size()==0);
    }
}
