package ca.jrvs.apps.twitter.CrdDao;

import ca.jrvs.apps.twitter.HttpHelper.HttpHelper;
import ca.jrvs.apps.twitter.HttpHelper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;


import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class TwitterDaoIntTest {
    private TwitterDao dao;
    Double lat = 1d;
    Double lon = -1d;
    String hashTag = "#create_test";
    String text = "sometext_test" + hashTag + " " + System.currentTimeMillis();


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
        this.dao = new TwitterDao(httpHelper);
    }

    @Test
    public void create() throws Exception{
        Tweet postTweet;

        try {
            postTweet = TweetUtil.buildTweet(text,lon,lat);
        }catch (Exception e){
            throw new Exception("Unable to create tweet with coordinate", e);
        }

        //System.out.println(JsonUtil.toPrettyJson(postTweet));

        Tweet tweet = dao.create(postTweet);

        assertEquals(text, tweet.getText());

        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));

        assertTrue(hashTag.contains(tweet.getEntites().getHashtags().get(0).getText()));

    }

    @Test
    public void findById() throws Exception {

        String id = "1234567890";
        String expectedText = "@someone sometext #create_test";
        Tweet tweet = dao.findById(id);

        assertEquals(expectedText, tweet.getText());

        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));

        assertTrue(hashTag.contains(tweet.getEntites().getHashtags().get(0).getText()));

    }

    @Test
    public void deleteById() throws Exception {

        Tweet tweet;

        try {
            tweet = TweetUtil.buildTweet(text,lon,lat);
        }catch (Exception e){
            throw new Exception("Unable to create tweet with coordinate in del part", e);
        }

        Tweet createTweet = dao.create(tweet);

        String id = createTweet.getId_str();
        Tweet delTweet = dao.deleteById(id);

        assertNotNull(delTweet.getCoordinates());
        assertEquals(2, delTweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, delTweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, delTweet.getCoordinates().getCoordinates().get(1));

    }


}
