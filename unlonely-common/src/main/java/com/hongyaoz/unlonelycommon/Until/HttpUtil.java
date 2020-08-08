package com.hongyaoz.unlonelycommon.Until;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;
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
}
