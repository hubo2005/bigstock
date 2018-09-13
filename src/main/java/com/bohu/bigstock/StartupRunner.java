package com.bohu.bigstock;

import com.imadcn.framework.idworker.generator.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;

//需要项目启动就运行下面示例的话，取消下面@Component注解即可
//@Component
public class StartupRunner implements CommandLineRunner {




    // snowflakeGenerator 可以有个优化，基于K8S POD IP作为 唯一 workid， 参考下面，关键workid生成
    // https://github.com/johnhuang-cn/snowflake-uid/blob/master/src/main/java/net/xdevelop/snowflake/SnowflakeUidGenerator.java
    //public static long getWorkerIdByIP(int bits) throws UidGenerateException {
    //    	int shift = 64 - bits;
    //    	try {
    //			InetAddress address = InetAddress.getLocalHost();
    //			long ip = IpUtils.ipV4ToLong(address.getHostAddress());
    //			long workerId = (ip << shift) >>> shift;
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