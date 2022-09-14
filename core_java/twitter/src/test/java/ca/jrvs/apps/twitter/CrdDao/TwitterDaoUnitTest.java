package ca.jrvs.apps.twitter.CrdDao;


import ca.jrvs.apps.twitter.HttpHelper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class TwitterDaoUnitTest {
    @Mock
    HttpHelper mockHelper;

    @InjectMocks
    TwitterDao dao;

    String tweetJsonStr = "{\n"
            + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
            + "   \"id\":1097607853932564480,\n"
            + "   \"id_str\":\"1097607853932564480\",\n"
            + "   \"text\":\"test with loc223\",\n"
            + "   \"entities\":{\n"
            + "      \"hashtags\":[],"
            + "      \"user_mentions\":[]"
            + "   },\n"
            + "   \"coordinates\": {"
            + "           \"coordinates\" : [ 10.1, 10.1 ],\n"
            + "           \"type\" : \"Point\"\n"
            + "   },\n"
            + "   \"retweet_count\":0,\n"
            + "   \"favorite_count\":0,\n"
            + "   \"favorited\":false,\n"
            + "   \"retweeted\":false\n"
            + "}";

    public Tweet createTweetHelper() throws Exception{
        Integer lat = 1;
        Integer lon = -1;
        String hashTag = "#create_test";
        String text = "@someone sometext " + hashTag + " " + System.currentTimeMillis();

        Coordinates coordinates = new Coordinates();
        Tweet tweet = new Tweet();

        try {
            coordinates.setCoordinates(Arrays.asList(lon, lat));
            coordinates.setType("Point");
            tweet.setText(text);
            tweet.setCoordinates(coordinates);
        }catch (Exception e){
            throw new Exception("Unable to create tweet with coordinate", e);
        }
        System.out.println(tweet.toString());

        Tweet createTweet = dao.create(tweet);
        return createTweet;
    }
    @Test
    public void showTweet() throws Exception {
        String hashTag = "tacotuesday";
        String text = "@paulwguevarra guess what day it is? It really really is "+ hashTag + " " + System.currentTimeMillis();
        float lat = -10f;
        float lon = 10f;

        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try{
            //dao.create((TweetUtil.buildTweet(text,lon, lat)));
            createTweetHelper();
            fail();
        }catch(RuntimeException e){
            assertTrue(true);
        }

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonParser.toObjectFromJSON(tweetJsonStr,Tweet.class);

        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        Tweet tweet = spyDao.create(createTweetHelper());
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void shouldFindById() throws IOException {
        String JsonStr = "{\n"
                + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
                + "   \"id\":1097607853932564480,\n"
                + "   \"id_str\":\"1097607853932564480\",\n"
                + "   \"text\":\"test with loc223\",\n"
                + "   \"entities\":{\n"
                + "      \"hashtags\":[],\n"
                + "      \"user_mentions\":[]\n"
                + "   },\n"
                + "   \"coordinates\":null,\n"
                + "   \"retweet_count\":0,\n"
                + "   \"favorite_count\":0,\n"
                + "   \"favorited\":false,\n"
                + "   \"retweeted\":false\n"
                + "}";
        String id = "1097607853932564480";

        //test failed request
        //when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            dao.findById(id);
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        //test successful request
        //when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonParser.toObjectFromJSON(JsonStr, Tweet.class);
        //mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        Tweet tweet = spyDao.findById(id);
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void deleteTweet() throws IOException {
        String JsonStr = "{\n"
                + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
                + "   \"id\":1097607853932564480,\n"
                + "   \"id_str\":\"1097607853932564480\",\n"
                + "   \"text\":\"test with loc223\",\n"
                + "   \"entities\":{\n"
                + "      \"hashtags\":[],\n"
                + "      \"user_mentions\":[]\n"
                + "   },\n"
                + "   \"coordinates\":null,\n"
                + "   \"retweet_count\":0,\n"
                + "   \"favorite_count\":0,\n"
                + "   \"favorited\":false,\n"
                + "   \"retweeted\":false\n"
                + "}";
        String id = "1097607853932564480";

        //test failed request
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            dao.deleteById(id);
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        //test successful request
        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonParser.toObjectFromJSON(JsonStr, Tweet.class);
        //mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        Tweet tweet = spyDao.deleteById(id);
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

}
