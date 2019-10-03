package com.unknown.support.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <br>(c) Copyright koujiang901123@sima.com
 * <br>@description    :
 * <br>@file_name  :null.java
 * <br>@system_name    :platform
 * <br>@author    :Administrator
 * <br>@create_time    :2019/1/15 10:23
 * <br>@mender    :(Please add the modifier name)
 * <br>@Modified   :(Please add modification date)
 * <br>@varsion       :v1.0.0
 */
@Slf4j
public class HttpClientTookit {

    //类加载的时候 设置最大连接数 和 每个路由的最大连接数
    //最大连接数
    private static int maxTotal = 200;
    //默认的每个路由的最大连接数(每路由最高并发数量)，具体依据业务来定
    private static int defaultMaxPerRoute = 100;
    //请求客户端
    //private static CloseableHttpClient httpClient;
    //请求配置
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15_000)                //读超时
            .setConnectTimeout(15_000)               //设置获取连接超时
            .setConnectionRequestTimeout(15_000)    //设置请求连接超时
            .setMaxRedirects(10)                    //最大重定向次数
            //.setRedirectsEnabled(true)            //默认允许自动重定向
            //.setContentCompressionEnabled(false)  //自动gzip
            .build();
    // 请求连接池
    private static PoolingHttpClientConnectionManager connectionManager;

    private static CloseableHttpClient getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {

        INSTANCE;

        private CloseableHttpClient httpClient;
        Singleton() {
            //2、构建连接池工厂
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", new PlainConnectionSocketFactory()).build();
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            connectionManager.setMaxTotal(maxTotal);                                        //最大连接数
            connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);                    //默认的每个路由的最大连接数

            //请求失败时,进行请求重试
            // 采用默认DefaultHttpRequestRetryHandler
            /*HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
                @Override
                public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                    if (i > 3){
                        //重试超过3次,放弃请求
                        log.error("retry has more than 3 time, give up request");
                        return false;
                    }
                    if (e instanceof NoHttpResponseException){
                        //服务器没有响应,可能是服务器断开了连接,应该重试
                        log.error("receive no response from server, retry");
                        return true;
                    }
                    if (e instanceof SSLHandshakeException){
                        // SSL握手异常
                        log.error("SSL hand shake exception");
                        return false;
                    }
                    if (e instanceof InterruptedIOException){
                        //超时
                        log.error("InterruptedIOException");
                        return false;
                    }
                    if (e instanceof UnknownHostException){
                        // 服务器不可达
                        log.error("server host unknown");
                        return false;
                    }
                    if (e instanceof ConnectTimeoutException){
                        // 连接超时
                        log.error("Connection Time out");
                        return false;
                    }
                    if (e instanceof SSLException){
                        log.error("SSLException");
                        return false;
                    }

                    HttpClientContext context = HttpClientContext.adapt(httpContext);
                    HttpRequest request = context.getRequest();
                    if (!(request instanceof HttpEntityEnclosingRequest)){
                        //如果请求不是关闭连接的请求
                        return true;
                    }
                    return false;
                }
            };*/

            // 有少数固定客户端，长时间极高频次的访问服务器，启用keep-alive非常合适
            //默认采用DefaultConnectionKeepAliveStrategy(ConnectionKeepAliveStrategy)
            /*ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
                @Override
                public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                    HeaderElementIterator it = new BasicHeaderElementIterator
                            (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                    while (it.hasNext()) {
                        HeaderElement he = it.nextElement();
                        String param = he.getName();
                        String value = he.getValue();
                        if (value != null && param.equalsIgnoreCase
                                ("timeout")) {
                            return Long.parseLong(value) * 1000;
                        }
                    }
                    return 60 * 1000;//如果没有约定，则默认定义时长为60s
                }
            };*/
            /*httpBuilder = HttpClients.custom()
                .setConnectionManager(connectionManager);
            httpClient = httpBuilder.build();*/

            this.httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        }

        CloseableHttpClient getInstance() {
            return httpClient;
        }
    }

    //发送 POST请求 params 参数(格式:key1=value1&key2=value2
    public static String sendHttpPost(String httpUrl, String params) throws Exception {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            //设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            throw e;
        }
        return sendHttpPost(httpPost);
    }

    //发送POST请求
    public static String sendHttpPost(String httpUrl, Map<String, Object> maps) throws Exception {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key) != null ? maps.get(key).toString() : null));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        return sendHttpPost(httpPost);
    }

    //发送POST请求
    private static String sendHttpPost(HttpPost httpPost) throws Exception {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String result = null;
        try {
            //1、创建默认的httpClient实例.
            CloseableHttpClient httpClient = HttpClients.createDefault();

            httpPost.setConfig(requestConfig);
            //2、执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            throw e;
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(entity);
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    //发送GET请求
    public static String sendHttpGet(String httpUrl, Map<String, String> maps) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(httpUrl);
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        uriBuilder.addParameters(nameValuePairs);
        // 创建get请求
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return sendHttpGet(httpGet);
    }

    //发送GET请求
    public static String sendHttpGet(String httpUrl, Map<String, String> maps, Map<String, String> headers) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(httpUrl);
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        uriBuilder.addParameters(nameValuePairs);
        // 创建get请求
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpGet.addHeader(entry.getKey(),entry.getValue());
        }
        return sendHttpGet(httpGet);
    }

    //发送GET请求
    private static String sendHttpGet(HttpGet httpGet) throws Exception {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = getInstance().execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            throw e;
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(entity);
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return responseContent;
    }
}