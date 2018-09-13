package com.bohu.bigstock.finance.JQK;

import com.bohu.bigstock.finance.domain.HistoricalQuote;
import com.bohu.bigstock.finance.domain.Stock;
import com.bohu.bigstock.finance.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JQKStockCollection {
    private static final Logger log = LoggerFactory.getLogger(JQKStockCollection.class);
    private static final String QUOTES_CSV_DELIMITER = "\t";

    public List<Stock> loadStockSymbolAndNameFromResourceFile(String filePath){

        List<Stock> stockSymbolNameList = new ArrayList<Stock>(100);
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = null;
        try {
        File symbolNameFile = new File(classLoader.getResource(filePath).getFile());
        inputStream = new FileInputStream(symbolNameFile);
        InputStreamReader is = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(is);
        br.readLine(); // skip the first line
        // Parse CSV
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            log.info("Parsing CSV line: " + Utils.unescape(line));
            Stock stock = this.parseCSVLine(line);
            if (stock!=null) stockSymbolNameList.add(stock);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream !=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return stockSymbolNameList;
    }

    private Stock parseCSVLine(String line) {
        String[] data = line.split(QUOTES_CSV_DELIMITER);
//        System.out.println("Line====="+line);
        if(data[0]!=null && data[1]!=null){
            String symbolWithoutExchangeCode = data[0].substring(2);
            String stockName = data[1];
//          System.out.println(symbolWithoutExchangeCode+"  "+stockName);
            return new Stock(symbolWithoutExchangeCode,stockName);
        }
        return null;

    }


}
