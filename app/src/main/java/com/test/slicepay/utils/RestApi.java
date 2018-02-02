package com.test.slicepay.utils;

/**
 * Created by pardypanda05 on 2/2/18.
 */

public class RestApi {

//https://api.flickr.com/services/rest/
    private static int PER_PAGE_COUNT = 5;
    private static String RESPONSE_FORMAT = "json";
    private static String API_KEY = "9f89151d82e427401680cd48dd2d5cf5";
    private static String globalURL = "https://api.flickr.com/services/rest/";


    public static String getImageListUrl(int pageNumber){
        return globalURL+"?method=flickr.interestingness.getList&api_key="+API_KEY+"&per_page="+PER_PAGE_COUNT+"&page="+pageNumber+"&format="+RESPONSE_FORMAT+"&nojsoncallback=1";
    }
}
