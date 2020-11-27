package com.example.lb2;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpDataGetter implements Runnable {
    private String data;
    private String url;

    public HttpDataGetter(String url){this.url = url;}


    @Override
    public void run() {
        try{
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent","Chrome");
            con.setRequestProperty("Accept-Charset","UTF-8");
            InputStream response  = con.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            data = s.hasNext() ? s.next() : "";
        }
        catch (Exception e){
            data = e.getMessage();
        }
    }

    public String getData(){
        Thread thread = new Thread(this);
        thread.start();
        try{
            thread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return data;
    }
}
