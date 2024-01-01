package com.kk.tomcat.handler;

import com.kk.tomcat.KkTomcat;
import com.kk.tomcat.http.KkRequest;
import com.kk.tomcat.http.KkResponse;
import com.kk.tomcat.servlet.KkHttpServlet;

import java.io.*;
import java.net.Socket;

/**
 * @author KK
 * @version 2.0
 * 处理 HTTP 请求的线程对象
 */
public class KkRequestHandler implements Runnable {
    private Socket socket = null;

    public KkRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 封装
            KkRequest kkRequest = new KkRequest(this.socket.getInputStream());
            KkResponse kkResponse = new KkResponse(this.socket.getOutputStream());

            // 从容器中得到对象
            String uri = kkRequest.getUri();
            String servletName = KkTomcat.servletUrlMapping.get(uri);
            if (null == servletName) {
                servletName = "";
            }
            KkHttpServlet kkHttpServlet = KkTomcat.servletMapping.get(servletName);
            if (null != kkHttpServlet) {
                kkHttpServlet.service(kkRequest, kkResponse);
            } else {
                // 无 Servlet 信息，返回 404
                String response = kkResponse.getRespHeader() + "<h1>404 Not Found</h1>";
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(response.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
