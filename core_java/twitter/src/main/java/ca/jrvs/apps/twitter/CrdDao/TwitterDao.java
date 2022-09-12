package ca.jrvs.apps.twitter.CrdDao;

import org.springframework.beans.factory.annotation.Autowired;

public class TwitterDao implements ca.jrvs.apps.twitter.dao.CrdDao<Tweet, String> {
    //URI constants
    private static final String API_BASE_URI = "https://api.twitter.come";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";

    //URI symbol
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    //Response code
    private static final int HTTP_OK = 200;

    private ca.jrvs.apps.twitter.dao.helper.HttpHelper httpHelper;

    @Autowired
    public TwitterDao(ca.jrvs.apps.twitter.dao.helper.HttpHelper httpHelper){
        this.httpHelper = httpHelper;
    }
}

