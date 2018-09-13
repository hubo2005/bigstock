package com.bohu.bigstock.mock;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MockedServer {
    private static final Logger log = LoggerFactory.getLogger(MockedServer.class);

    private static boolean started = false;

    public static MockWebServer sinaMockServer;
    public static MockWebServer neteaseMockServer;

    @BeforeClass
    public static void startServers() {
        if(started) {
            return;
        }
        started = true;
        sinaMockServer = new MockWebServer();
        neteaseMockServer = new MockWebServer();
        try {
            sinaMockServer.start();
            neteaseMockServer.start();
        } catch (IOException e) {
            log.error("Unable to start mock web server", e);
        }
        String quotesBaseUrl = "http://localhost:" + sinaMockServer.getPort() + "/downxls.php";
        String histQuotesBaseUrl = "http://localhost:" + neteaseMockServer.getPort() + "/service/chddata.html";

        System.setProperty("sina.baseurl.quotes", quotesBaseUrl);
        System.setProperty("netease.baseurl.quotes", histQuotesBaseUrl);

        final Dispatcher dispatcher = new MockDispatcher();
        sinaMockServer.setDispatcher(dispatcher);
        neteaseMockServer.setDispatcher(dispatcher);
    }

}
