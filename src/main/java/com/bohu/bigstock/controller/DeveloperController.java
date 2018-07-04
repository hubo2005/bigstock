package com.bohu.bigstock.controller;

import com.bohu.bigstock.finance.StockService;

import com.bohu.bigstock.finance.domain.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class DeveloperController {

    @Autowired
    private StockService stockService;

    @RequestMapping(value = "/helloworld.html", method = RequestMethod.GET)
    public String helloworld() {
        return "helloworld";
    }

    @RequestMapping(value="/helloProcess.json", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public String helloJson() throws Exception{
        Calendar c1=Calendar.getInstance();
        c1.set(2018,Calendar.MARCH,18);

        Calendar today = (Calendar) c1.clone();
        today.set(Calendar.MONTH,4);
        System.out.println("in hellworldJson method");
        //StockService.getHistoricalQuoteByDay("600727",c1,today);

        StockService.getStockTradeDetailsByDay("600727",c1);

        return "{\"data\": data345}";
    }
}
