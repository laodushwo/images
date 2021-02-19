package org.spring.springboot.util;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class WeChatTokenUtil {
    //获取access_token的url
    private static final String BASE_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?";
    //固定值
    private static final String GRANT_TYPE = "client_credential";
    //自己的AppID
    private static final String APP_ID = "wxe11b10afcf21067c";
    //自己的AppSecret
    private static final String SECRET = "677f690add471cbec764e4463e807966";

    public static String getToken(){
        String url = BASE_TOKEN_URL + "grant_type="+GRANT_TYPE+"&appid="+APP_ID+"&secret="+SECRET;
        try{
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            JSONObject jsonObject = JSONObject.parseObject(result);
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
            return "";
        }
    }

}