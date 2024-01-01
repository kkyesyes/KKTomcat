package com.kk.tomcat.http;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author KK
 * @version 1.0
 */
public class KkResponse {
    private OutputStream outputStream = null;
    private final String respHeader = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/html;charset=utf-8\r\n\r\n";

    public KkResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public String getRespHeader() {
        return respHeader;
    }
}
