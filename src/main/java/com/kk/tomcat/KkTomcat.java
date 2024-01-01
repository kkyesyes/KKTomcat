package com.kk.tomcat;

import com.kk.tomcat.handler.KkRequestHandler;
import com.kk.tomcat.servlet.KkHttpServlet;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author KK
 * @version 3.0
 * Tomcat主进程
 */
public class KkTomcat {
    public static final ConcurrentHashMap<String, KkHttpServlet> servletMapping =
            new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, String> servletUrlMapping =
            new ConcurrentHashMap<>();

    public static void main(String[] args){
        KkTomcat kkTomcat = new KkTomcat();
        kkTomcat.init();
        kkTomcat.run();
    }

    /**
     * 启动容器
     */
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("[ INFO ] Listening at 8080");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                KkRequestHandler kkRequestHandler = new KkRequestHandler(socket);
                new Thread(kkRequestHandler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对两个容器初始化
     */
    public void init() {
        String path = KkTomcat.class.getResource("/").getPath();
        SAXReader saxReader = new SAXReader();
        try {
            // 获取 web.xml 路径
            Document document = saxReader.read(new File(path + "web.xml"));
            // 获取根元素
            Element rootElement = document.getRootElement();
            // 根元素下的所有元素
            List<Element> elements = rootElement.elements();
            // 遍历并过滤
            for (Element element : elements) {
                if ("servlet".equalsIgnoreCase(element.getName())) {
                    // 是一个 servlet 配置
                    Element servletName = element.element("servlet-name");
                    Element servletClass = element.element("servlet-class");
                    KkTomcat.servletMapping.put(servletName.getText().trim(),
                            (KkHttpServlet) Class.forName(servletClass.getText().trim()).newInstance());
                } else if ("servlet-mapping".equalsIgnoreCase(element.getName())) {
                    // 是一个 servlet-mapping 配置
                    Element servletName = element.element("servlet-name");
                    Element urlPattern = element.element("url-pattern");
                    KkTomcat.servletUrlMapping.put(urlPattern.getText().trim(),
                            servletName.getText().trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
