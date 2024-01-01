package com.kk.tomcat.servlet;

import com.kk.tomcat.http.KkRequest;
import com.kk.tomcat.http.KkResponse;

import java.io.IOException;

/**
 * @author KK
 * @version 1.0
 */
public abstract class KkHttpServlet implements KkServlet {
    @Override
    public void service(KkRequest req, KkResponse resp) throws IOException {
        if ("GET".equalsIgnoreCase(req.getMethod())) {
            this.doGet(req, resp);
        } else {
            this.doPost(req, resp);
        }
    }

    public abstract void doGet(KkRequest req, KkResponse resp);

    public abstract void doPost(KkRequest req, KkResponse resp);
}
