package ca.jrvs.apps.twitter.Controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ca.jrvs.apps.twitter.service.Service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class TwitterControllerUnitTest {

    @Mock
    Service service;

    @InjectMocks
    TwitterController controller;
    Tweet newTweet = TweetUtil.buildTweet("test", 50d, 50d);

    @Test
    public void postTweet() {

        when(service.postTweet(any())).thenReturn(newTweet);
        Tweet tweet = controller.postTweet(
                new String[]{"post", "test", String.format("%s:%s", 50d, 50d)});
        assertNotNull(tweet);
        assertEquals("test", tweet.getText());

    }

    @Test
    public void showTweet() {
        String id = "";
        String fields = "created_at,id,id_str,text,entities,coordinates,retweet_count,favorite_count,favorited,retweeted";
        when(service.showTweet(anyString(), any())).thenReturn(newTweet);
        Tweet postTweet = controller.postTweet(
                new String[]{"show", id, fields});

        assertEquals(postTweet.getText(), newTweet.getText());

    }

    @Test
    public void deleteTweet() {
        String idFields = "";
        List<Tweet> testTweets = new ArrayList<Tweet>() {
        };
        testTweets.add(newTweet);
        testTweets.add(newTweet);
        when(service.deleteTweets(any())).thenReturn(testTweets);

        List<Tweet> tweets = controller.deleteTweet(new String[]{"delete", idFields});
        tweets.stream().forEach(tweet -> {
            assertNotNull(tweet);
            assertEquals("test", tweet.getText());
        });
    }
}
