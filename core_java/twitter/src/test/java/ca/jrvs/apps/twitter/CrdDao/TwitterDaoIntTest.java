package ca.jrvs.apps.twitter.CrdDao;

import ca.jrvs.apps.twitter.HttpHelper.HttpHelper;
import ca.jrvs.apps.twitter.HttpHelper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;


import static org.junit.Assert.*;

import net.minidev.json.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class TwitterDaoIntTest {
    private TwitterDao dao;
    Integer lat = 1;
    Integer lon = -1;
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
        Coordinates coordinates = new Coordinates();
        Tweet tweet = new Tweet();

        try {
            coordinates.setCoordinates(Arrays.asList(lon, lat));
            coordinates.setType("Point");
            tweet.setText(text);
            tweet.setCoordinates(coordinates);
            //Tweet postTweet = TweetUtil.buildTweet(text,lon,lat);
        }catch (Exception e){
            throw new Exception("Unable to create tweet with coordinate", e);
        }
        System.out.println(tweet.toString());
        //System.out.println(JsonUtil.toPrettyJson(postTweet));

        Tweet createTweet = dao.create(tweet);

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
        Coordinates coordinates = new Coordinates();
        Tweet tweet = new Tweet();

        try {
            coordinates.setCoordinates(Arrays.asList(lon, lat));
            coordinates.setType("Point");
            tweet.setText(text);
            tweet.setCoordinates(coordinates);
        }catch (Exception e){
            throw new Exception("Unable to create tweet with coordinate in del part", e);
        }
        System.out.println(tweet.toString());

        Tweet createTweet = dao.create(tweet);

        String id = createTweet.getId_str();
        Tweet delTweet = dao.deleteById(id);

        assertNotNull(delTweet.getCoordinates());
        assertEquals(2, delTweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, delTweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, delTweet.getCoordinates().getCoordinates().get(1));

    }


}
