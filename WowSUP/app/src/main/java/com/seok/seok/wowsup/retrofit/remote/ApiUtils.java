package com.seok.seok.wowsup.retrofit.remote;

public class ApiUtils {
    /*
    Server API return value
    0 = Success , 1 = Failed, 2 = etc..
    */

    //Server /var/www/html 의 주소
    public static final String BASE_URL = "http://www.heywowsup.com/test/";
    public static LoginService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(LoginService.class);
    }
}
