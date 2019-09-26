package org.apache.pricecontroller.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.pricecontroller.api.MessageService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageServiceImpl implements MessageService {

    @Override
    public boolean send(String from, String to, String content) {
        try {
            String token = getToken();
            return sendMessage(from, to, content, token);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to send message " + from + " " + to + " " + content, e);
        } catch (UnirestException e) {
            throw new IllegalStateException("Unable to get token", e);
        }
    }

    private boolean sendMessage(String from, String to, String content, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String params = String.format("csrf_token=%s&message_unicode=0&messages_count=1&recipients=%s&total_recipients=0&recipient=%s&message_body=%s&undefined=", token, from, to, content);
        RequestBody body = RequestBody.create(mediaType, params);
        Request request = new Request.Builder()
                .url("http://www.magtifun.ge/scripts/sms_send.php")
                .post(body)
                .addHeader("Pragma", "no-cache")
                .addHeader("Origin", "http://www.magtifun.ge")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept-Language", "en-US,en;q=0.9,ka;q=0.8")
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("Cookie", "User=uh5vi0rksai01bkbh0f9le77f5; __utma=181079995.1988700087.1567957833.1567957833.1567957833.1; __utmc=181079995; __utmz=181079995.1567957833.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); __utmt=1; __utmb=181079995.5.10.1567957833")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", "http://www.magtifun.ge/index.php?page=2&lang=ge")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "03b6711d-4267-43f0-9639-c44093398003")
                .build();

        Response response;

        response = client.newCall(request).execute();

        return response.body().contentLength() < 100;
    }

    private String getToken() throws UnirestException {
        HttpResponse<String> response = Unirest.get("http://www.magtifun.ge/index.php?page=2&lang=ge")
                .header("Connection", "keep-alive")
                .header("Pragma", "no-cache")
                .header("Cache-Control", "no-cache")
                .header("Upgrade-Insecure-Requests", "1")
                .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("Referer", "http://www.magtifun.ge/index.php?page=11&lang=ge")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "en-US,en;q=0.9,ka;q=0.8")
                .header("Cookie", "User=uh5vi0rksai01bkbh0f9le77f5; __utmc=181079995; __utma=181079995.1988700087.1567957833.1567957833.1567964536.2; __utmz=181079995.1567964536.2.2.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); user_name=; user_password=; __utmt=1; __utmb=181079995.18.10.1567964536")
                .header("cache-control", "no-cache")
                .header("Postman-Token", "e3e671bb-0323-4d61-a8cf-7cef83d9df2b")
                .asString();


        Document dom = Jsoup.parse(response.getBody());

        return dom.getElementById("sms_form").getElementsByTag("input").get(0).val();
    }
}
