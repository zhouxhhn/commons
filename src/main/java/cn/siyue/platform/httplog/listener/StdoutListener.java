package cn.siyue.platform.httplog.listener;

import cn.siyue.platform.httplog.printstream.ErrLogbackPrintStream;
import cn.siyue.platform.httplog.printstream.LogbackPrintStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StdoutListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StdoutListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        LogbackPrintStream printStream = new LogbackPrintStream(System.out);
        ErrLogbackPrintStream errPrintStream = new ErrLogbackPrintStream(System.err);
        System.setOut(printStream);
        System.setErr(errPrintStream);
    }
}