package ca.jrvs.apps.twitter.Controller;

import ca.jrvs.apps.twitter.CrdDao.TwitterDao;
import ca.jrvs.apps.twitter.HttpHelper.HttpHelper;
import ca.jrvs.apps.twitter.HttpHelper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.Service.TwitterService;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerIntTest {
    private TwitterDao dao;
    private TwitterService service;
    private TwitterController controller;
    String fields = "created_at,id,id_str,text,entities,coordinates,retweet_count,favorite_count,favorited,retweeted";
    @Before
    public void setup(){
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
        //setup dependency
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret,
                accessToken, tokenSecret);
        //pass dependency
        dao = new TwitterDao(httpHelper);
        service = new TwitterService(dao);
        controller = new TwitterController(new TwitterService(new TwitterDao(httpHelper)));
    }

    @Test
    public void postTweet() {
        String[] args = {"post", "test", "50:-50"};
        Tweet tweet = controller.postTweet(args);

        assertNotNull(tweet.getCoordinates());
        assertEquals("test", tweet.getText());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
    }

    @Test
    public void showTweet() {
        String id = "";
        String[] args = {"show", id, fields};
        Tweet tweet = controller.showTweet(args);

        assertNotNull(tweet.getCoordinates());
        assertEquals("test", tweet.getText());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
    }

    @Test
    public void deleteTweet() {
        String id = "";
        String[] args = {"delete", id};
        List<Tweet> tweets = controller.deleteTweet(args);

        Tweet tweet = tweets.get(0);
        assertNotNull(tweet.getCoordinates());
        assertEquals("test", tweet.getText());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());

    }

}