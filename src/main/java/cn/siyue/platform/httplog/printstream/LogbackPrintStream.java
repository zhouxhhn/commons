package cn.siyue.platform.httplog.printstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.io.PrintStream;

public class LogbackPrintStream extends PrintStream {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogbackPrintStream.class);

    public LogbackPrintStream(OutputStream out) {
        super(out);
    }

    public void printLog(Object msg) {
        LOGGER.info(String.valueOf(msg));
    }

    @Override
    public void print(boolean b) {
        super.print(b);
        printLog(b);
    }

    @Override
    public void print(char c) {
        super.print(c);
        printLog(c);
    }

    @Override
    public void print(int i) {
        super.print(i);
        printLog(i);
    }

    @Override
    public void print(long l) {
        super.print(l);
        printLog(l);
    }

    @Override
    public void print(float f) {
        super.print(f);
        printLog(f);
    }

    @Override
    public void print(double d) {
        super.print(d);
        printLog(d);
    }

    @Override
    public void print(char[] s) {
        super.print(s);
        printLog(s);
    }

    @Override
    public void print(String s) {
        super.print(s);
        printLog(s);
    }

    @Override
    public void print(Object obj) {
        super.print(obj);
        printLog(obj);
    }

}
