package com.hongyaoz.unlonelycommon.Until;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    public  static  JSONObject GET(String url,Map<String,String> map){
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        StringBuilder stringBuilder=new StringBuilder(url);
         int count=0;
        for(Map.Entry entry:map.entrySet()){
            if( count==0){
                stringBuilder.append("?"+entry.getKey()+"="+entry.getValue());
            }else {
                stringBuilder.append("&"+entry.getKey()+"="+entry.getValue());
            }
               count++;
        }
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(stringBuilder.toString());
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(stringBuilder.toString());

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(stringBuilder.toString());
        // 解析json
        JSONObject jsonObject = (JSONObject) JSONObject.parse(resultString);
        return  jsonObject;
    }

    public static JSONObject GET(String url) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(url);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解析json
        JSONObject jsonObject = (JSONObject) JSONObject.parse(resultString);
        return  jsonObject;
    }
    public static final String UTF_8 = "UTF-8";

    /**
     * 发送post请求
     *
     * @param url URL地址
     * @param socketTimeout 建立scoket连接超时时间
     * @param connectTimeout 建立数据传输连接超时时间
     * @param headers 请求头参数
     * @param params 请求参数
     * @param json 请求体(json)参数
     * @return String 响应报文
     * @throws Exception 异常
     */
    public static String sendPost(String url, int socketTimeout, int connectTimeout,
                                  Map<String, String> headers, Map<String, ?> params,
                                  String json) throws Exception {
        // URL处理: 请求参数拼接到URL中传递
        if (null != params && params.size() != 0) {
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }
            String uri = EntityUtils.toString(new UrlEncodedFormEntity(list, UTF_8));
            if (uri != null) {
                url = url.contains("?") ? (url + "&" + uri) : (url + "?" + uri);
            }
        }

        // 构建请求: HttpPost
        HttpPost post = new HttpPost(url);
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectTimeout)
                .build();
        post.setConfig(config);

        // 设置请求头: Header
        if (null != headers && headers.size() != 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // 设置请求体: HttpEntity
        if (null != json && !"".equals(json.trim())) {
            post.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        }

        // 调用并返回结果
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = HttpClients.createDefault();
            response = client.execute(post);
            return EntityUtils.toString(response.getEntity(), UTF_8);
        } finally {
            if (response != null) {
                response.close();
            }
            if (client != null) {
                client.close();
            }
        }
    }
}
