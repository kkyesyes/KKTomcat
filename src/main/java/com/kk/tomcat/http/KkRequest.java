package com.kk.tomcat.http;

import java.io.*;
import java.util.HashMap;

/**
 * @author KK
 * @version 1.0
 */
public class KkRequest {
    private String method;
    private String uri;
    private HashMap<String, String> parameterMapping = new HashMap<>();
    private InputStream inputStream = null;

    public KkRequest(InputStream inputStream) {
        // 对 HTTP 请求进行封装
        this.inputStream = inputStream;
        packHttpReq();
    }

    public void packHttpReq() {
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(this.inputStream, "utf-8"));

            String requestLine = bufferedReader.readLine();
            String[] reqFirstLine = requestLine.split(" ");
            this.method = reqFirstLine[0];
            int index = reqFirstLine[1].indexOf('?');
            if (index == -1) {
                this.uri = reqFirstLine[1];
            } else {
                this.uri = reqFirstLine[1].substring(0, index);
                String parameterPairsStr = reqFirstLine[1].substring(index + 1);
                String[] parameterPairs = parameterPairsStr.split("&");
                if (null != parameterPairsStr && !"".equals(parameterPairsStr)) {
                    for (String parameterPair : parameterPairs) {
                        String[] parameterVal = parameterPair.split("=");
                        if (parameterVal.length == 2) {
                            this.parameterMapping.put(parameterVal[0], parameterVal[1]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getParameter(String name) {
        if (this.parameterMapping.containsKey(name)) {
            return this.parameterMapping.get(name);
        } else {
            return "";
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
