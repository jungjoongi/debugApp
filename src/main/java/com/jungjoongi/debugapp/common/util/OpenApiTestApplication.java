package com.jungjoongi.debugapp.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public final class OpenApiTestApplication {
    private final static String REQUEST_METHOD = "GET";
    private final static String DOMAIN = "https://api-gateway.coupang.com";
    private final static String URL = "/v2/providers/affiliate_open_api/apis/openapi/v1/products/bestcategories/%s?limit=100";
    // Replace with your own ACCESS_KEY and SECRET_KEY
// 준기
    private final static String ACCESS_KEY = "1b6e6d78-b61e-42eb-b51d-b4546db56b34";
    private final static String SECRET_KEY = "f681101e6765bdfa8ca427da59abf1d01c15d2aa";
    private final static String[][] BEST_URL_CODE = {{"1001", "1002", "1003", "1004",	"1005",	"1006",	"1007",	"1008",	"1010",	"1011",	"1012",	"1013",	"1014",	"1015",	"1016",	"1017",	"1018",	"1019",	"1020",	"1021",	"1024",	"1025",	"1026",	"1029"}, {"1001", "1002", "1003", "1004",	"1005",	"1006",	"1007",	"1008",	"1010",	"1011",	"1012",	"1013",	"1014",	"1015",	"1016",	"1017",	"1018",	"1019",	"1020",	"1021",	"1024",	"1025",	"1026",	"1029"}};
    // 찬미
//    private final static String ACCESS_KEY = "3fbd939d-f9f7-4bfd-8636-7ccf49a945ef";
//    private final static String SECRET_KEY = "50d602ca724242b24e9de6a88cc591cb65e3737f";
//    private final static String[][] BEST_URL_CODE = {{"1001", "1002", "1003", "1004",	"1005",	"1006",	"1007",	"1008",	"1010",	"1011",	"1012",	"1013",	"1014",	"1015",	"1016",	"1017",	"1018",	"1019",	"1020",	"1021",	"1024",	"1025",	"1026",	"1029"}, {"여성패션","남성패션","베이비패션 (0~3세)","여아패션 (3세 이상)","남아패션 (3세 이상)","스포츠패션","신발","가방&잡화","뷰티","출산&유아동","식품","주방용품","생활용품","홈인테리어","가전디지털","스포츠&레저","자동차용품","도서&음반&DVD","완구&취미","문구&오피스","헬스&건강식품","국내여행","해외여행","반려동물용품"}};

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