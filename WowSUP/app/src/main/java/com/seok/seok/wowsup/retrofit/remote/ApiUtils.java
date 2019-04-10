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
    public static ProfileService getProfileService(){
        return RetrofitClient.getClient(BASE_URL).create(ProfileService.class);
    }
    public static WordChartService getChartService(){
        return RetrofitClient.getClient(BASE_URL).create(WordChartService.class);
    }
    public static StoryService getStoryService() {
        return RetrofitClient.getClient(BASE_URL).create(StoryService.class);
    }
    public static StoreService getStoreService() {
        return RetrofitClient.getClient(BASE_URL).create(StoreService.class);
    }
}
