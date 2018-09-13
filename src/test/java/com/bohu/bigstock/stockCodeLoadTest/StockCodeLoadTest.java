package com.bohu.bigstock.stockCodeLoadTest;

import com.bohu.bigstock.finance.JQK.JQKStockCollection;
import com.bohu.bigstock.finance.StockService;
import com.bohu.bigstock.finance.domain.Stock;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class StockCodeLoadTest {

    @Test
    public void stockNameAndSymbolLoadTest(){
        JQKStockCollection jqkStockCollection = new JQKStockCollection();
        List<Stock> stockList = jqkStockCollection.loadStockSymbolAndNameFromResourceFile("stockCodeParse/stockcode.csv");
        assertNotNull(stockList);
        assertTrue(stockList.size() > 0);
    }
}
