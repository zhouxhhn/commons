package cn.siyue.platform.httplog.printstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;

public class ErrLogbackPrintStream extends LogbackPrintStream {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrLogbackPrintStream.class);

    public ErrLogbackPrintStream(OutputStream out) {
        super(out);
    }

    @Override
    public void printLog(Object msg) {
        LOGGER.error(String.valueOf(msg));
    }
}
