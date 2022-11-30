package com.weolbu.admin.common.util;

import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileWriter;
import java.io.IOException;

public final class OpenApiTestApplication {
    private final static String REQUEST_METHOD = "GET";
    private final static String DOMAIN = "https://api-gateway.coupang.com";
    private final static String URL = "/v2/providers/affiliate_open_api/apis/openapi/v1/products/bestcategories/%s?limit=100";
    // Replace with your own ACCESS_KEY and SECRET_KEY
// 준기
    private final static String ACCESS_KEY = "";
    private final static String SECRET_KEY = "";
    private final static String[][] BEST_URL_CODE = {{"1001", "1002", "1003", "1004",	"1005",	"1006",	"1007",	"1008",	"1010",	"1011",	"1012",	"1013",	"1014",	"1015",	"1016",	"1017",	"1018",	"1019",	"1020",	"1021",	"1024",	"1025",	"1026",	"1029"}, {"1001", "1002", "1003", "1004",	"1005",	"1006",	"1007",	"1008",	"1010",	"1011",	"1012",	"1013",	"1014",	"1015",	"1016",	"1017",	"1018",	"1019",	"1020",	"1021",	"1024",	"1025",	"1026",	"1029"}};

    private final static String REQUEST_JSON = "{\"\"}";

    public static void main(String[] args) throws IOException {
        for(int i = 0; i < BEST_URL_CODE[0].length; i++) {

            String httpResponse = OpenApiTestApplication.makeAuthorization(BEST_URL_CODE[0][i]);

            OpenApiTestApplication.makeFile(httpResponse, BEST_URL_CODE[1][i]);
            /*try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println("error : "+ e);
            }*/
        }
    }

    private static String makeAuthorization(String bestUrlCode) throws IOException {
        // Generate HMAC string
        String url = URL;
        url = String.format(url,bestUrlCode);
        String authorization = HmacGenerator.generate(REQUEST_METHOD, url, SECRET_KEY, ACCESS_KEY);

        // Send request
        StringEntity entity = new StringEntity("UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");


        HttpHost host = HttpHost.create(DOMAIN);
        HttpRequest request = RequestBuilder
                .get(url).setEntity(entity)
                .addHeader("Authorization", authorization)
                .build();

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(host, request);
        // verify
        return EntityUtils.toString(httpResponse.getEntity());
    }

    private static void makeFile(String httpStr, String bestUrlCode) {
       String saveStr = StringHelper.nvl(httpStr);
       if("".equals(saveStr)) {
           return;
       }

        String filePath = "c:\\Temp\\"+bestUrlCode+".json";
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(saveStr);

            fileWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}