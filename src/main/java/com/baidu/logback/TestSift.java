package com.baidu.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Created with IntelliJ IDEA.
 * User: mazhen01
 * Date: 2015/3/20
 * Time: 11:21
 */
public class TestSift {


    public static void main(String[] args) throws JoranException {

        String configFile = "src/main/resources/log/sync.xml";

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        lc.reset();
        configurator.setContext(lc);
        configurator.doConfigure(configFile);


        Logger logger = LoggerFactory.getLogger(TestSift.class);
        logger.debug("Application started");

        MDC.put("typeName", "Alice");
        logger.debug("Alice says hello");


        //StatusPrinter.print(lc);
    }

    static void usage(String msg) {
        System.err.println(msg);
        System.err.println("Usage: java " + TestSift.class.getName()
                + " configFile\n" + "   configFile a logback configuration file");
        System.exit(1);
    }
}
