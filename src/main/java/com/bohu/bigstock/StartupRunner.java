package com.bohu.bigstock;

import com.imadcn.framework.idworker.generator.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;

//需要项目启动就运行下面示例的话，取消下面@Component注解即可
//@Component
public class StartupRunner implements CommandLineRunner {




    @Autowired
    @Qualifier("snowflakeGenerator")
    private SnowflakeGenerator snowflakeGenerator;

    @Override
    public void run(String... strings) throws Exception {
//        System.out.println("hello----------------------------");
//        Stock stock = YahooFinance.get("600727.SS",false);
//        stock.print();
//        System.out.println(stock);
//        System.out.println(stock.getQuote().getAsk());
//        System.out.println(stock.getQuote().getDayHigh());
//        System.out.println(stock.getQuote().getVolume());
/*        for (int i=0; i<10000; i++ ) {
            Object object = snowflakeGenerator.nextId();
            System.out.println(object);
        }*/
    }
}