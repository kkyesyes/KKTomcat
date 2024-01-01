package com.kk.tomcat.servlet;

import com.kk.tomcat.http.KkRequest;
import com.kk.tomcat.http.KkResponse;

import java.io.IOException;

/**
 * @author KK
 * @version 1.0
 */
public interface KkServlet {
    void init() throws Exception;

    void service(KkRequest req, KkResponse resp) throws IOException;

    void destroy();
}
