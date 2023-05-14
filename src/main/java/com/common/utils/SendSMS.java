
package com.common.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Component
public class SendSMS {
    // @Value("${SMS.url}")
    private String url;
    //@Value("${SMS.appkey}")
    private String appkey;
    //@Value("${SMS.secretkey}")
    private String secretkey;

    public boolean send(String phone, String verifyCode) {

        MultiValueMap<String, Object> requestParam = new LinkedMultiValueMap<>();

        requestParam.add("appkey", appkey);
        requestParam.add("secretkey", secretkey);
        requestParam.add("phone", phone);
        String SMSContent = "";
        try {
            SMSContent = URLEncoder.encode(LangUtil.getMessage("SMS_BEFORE") + verifyCode + LangUtil.getMessage("SMS_TAIL"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        requestParam.add("content", SMSContent);

        RestTemplate restTemplate = new RestTemplate();
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(requestParam, headers);

        Map result = restTemplate.postForObject(url, httpEntity, Map.class);
        for (Object s : result.keySet()) {
            System.out.print(s + "->");
            System.out.println(String.valueOf(result.get(s)));
        }
        String code = String.valueOf(result.get("code"));
        return code.equals("0") ? true : false;
    }
}
