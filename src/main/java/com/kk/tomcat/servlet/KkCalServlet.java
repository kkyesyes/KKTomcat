package com.kk.tomcat.servlet;

import com.kk.tomcat.http.KkRequest;
import com.kk.tomcat.http.KkResponse;
import com.kk.tomcat.utils.WebUtils;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author KK
 * @version 1.0
 */
public class KkCalServlet extends KkHttpServlet {
    @Override
    public void doGet(KkRequest req, KkResponse resp) {
        int num1 = WebUtils.parseInt(req.getParameter("num1"), 0);
        int num2 = WebUtils.parseInt(req.getParameter("num2"), 0);
        int res = num1 + num2;
        OutputStream outputStream = resp.getOutputStream();
        String response = resp.getRespHeader() +
                "<h1>" + num1 + " + " + num2 + " = " + res + "</h1>";
        try {
            outputStream.write(response.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPost(KkRequest req, KkResponse resp) {
        this.doGet(req, resp);
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destroy() {

    }
}
