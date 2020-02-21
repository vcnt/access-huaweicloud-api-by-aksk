package com.company;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.company.AccessService;
import com.company.AccessServiceImpl;
import org.apache.http.HttpResponse;

import com.cloud.sdk.http.HttpMethodName;


public class Demo {

    //replace real region
    private static final String region = "";

    //replace real service name
    private static final String serviceName = "";

    public static void main(String[] args) {

        //replace real AK
        String ak = "";
        //replace real SK
        String sk = "";

        // get method
        //replace real url
        String url = "https://aos.cn-north-1.myhwclouds.com/v1.0/elbaas/listeners";
        //String url = "https://aos.cn-north-1.myhwclouds.com/v2/aos/templates";
        get(ak, sk, url);

        /*
        // post method
        //replace real url
        String postUrl = "urlString";
        //replace real body
        String postbody = "bodyString";
        post(ak, sk, postUrl, postbody);

        // put method
        //replace real body
        String putbody = "bodyString";
        //replace real url
        String putUrl = "urlString";
        put(ak, sk, putUrl, putbody);

        // delete method
        //replace real url
        String deleteUrl = "urlString";
        delete(ak, sk, deleteUrl);
        */
    }

    public static void put(String ak, String sk, String requestUrl,
                           String putBody) {

        AccessService accessService = null;
        try {
            accessService = new AccessServiceImpl(serviceName, region, ak, sk);
            URL url = new URL(requestUrl);
            HttpMethodName httpMethod = HttpMethodName.PUT;

            InputStream content = new ByteArrayInputStream(putBody.getBytes());
            HttpResponse response = accessService.access(url, content,
                    (long) putBody.getBytes().length, httpMethod);

            System.out.println(response.getStatusLine().getStatusCode());


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            accessService.close();
        }

    }

    public static void patch(String ak, String sk, String requestUrl,
                             String putBody) {

        AccessService accessService = null;
        try {
            accessService = new AccessServiceImpl(serviceName, region, ak, sk);
            URL url = new URL(requestUrl);
            HttpMethodName httpMethod = HttpMethodName.PATCH;
            InputStream content = new ByteArrayInputStream(putBody.getBytes());
            HttpResponse response = accessService.access(url, content,
                    (long) putBody.getBytes().length, httpMethod);

            System.out.println(convertStreamToString(response.getEntity()
                    .getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            accessService.close();
        }

    }

    public static void delete(String ak, String sk, String requestUrl) {

        AccessService accessService = null;

        try {
            accessService = new AccessServiceImpl(serviceName, region, ak, sk);
            URL url = new URL(requestUrl);
            HttpMethodName httpMethod = HttpMethodName.DELETE;

            HttpResponse response = accessService.access(url, httpMethod);
            System.out.println(convertStreamToString(response.getEntity()
                    .getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            accessService.close();
        }

    }

    public static void get(String ak, String sk, String requestUrl) {

        AccessService accessService = null;

        try {
            accessService = new AccessServiceImpl(serviceName, region, ak, sk);
            URL url = new URL(requestUrl);
            HttpMethodName httpMethod = HttpMethodName.GET;
            HttpResponse response;
            response = accessService.access(url, httpMethod);
            System.out.println(convertStreamToString(response.getEntity()
                    .getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            accessService.close();
        }

    }

    public static void post(String ak, String sk, String requestUrl,
                            String postbody) {

        AccessService accessService = new AccessServiceImpl(serviceName,
                region, ak, sk);
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStream content = new ByteArrayInputStream(postbody.getBytes());
        HttpMethodName httpMethod = HttpMethodName.POST;
        HttpResponse response;

        try {
            response = accessService.access(url, content,
                    (long) postbody.getBytes().length, httpMethod);
            System.out.println(convertStreamToString(response.getEntity()
                    .getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            accessService.close();
        }
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}
