package ca.jrvs.apps.twitter.Service;

import ca.jrvs.apps.twitter.CrdDao.TwitterDao;
import ca.jrvs.apps.twitter.HttpHelper.HttpHelper;
import ca.jrvs.apps.twitter.HttpHelper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import oauth.signpost.exception.OAuthException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterServiceIntTest {
    private TwitterDao dao;
    private TwitterService service;

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
        service = new TwitterService(new TwitterDao(httpHelper));
    }

    @Test
    public void postTweet() throws Exception {
        Tweet tweet = TweetUtil.buildTweet("service_TweetTest" + System.currentTimeMillis(), 1d, -1d);
        Tweet postTweet = service.postTweet(tweet);;

        assertEquals(tweet.getText(), postTweet.getText());
        assertNotNull(postTweet.getCoordinates());
        assertEquals(2, postTweet.getCoordinates().getCoordinates().size());
        assertEquals(tweet.getCoordinates().getCoordinates().get(0), postTweet.getCoordinates().getCoordinates().get(0));
        assertEquals(tweet.getCoordinates().getCoordinates().get(1), postTweet.getCoordinates().getCoordinates().get(1));

    }

    @Test
    public void showTweet() {
        String id = "";
        String[] fields = {
                "created_at",
                "id",
                "id_str",
                "text",
                "entities",
                "coordinates",
                "retweet_count",
                "favorite_count",
                "favorited",
                "retweeted"
        };
        Tweet tweet = TweetUtil.buildTweet("service_TweetTest" + System.currentTimeMillis(), 1d, -1d);
        Tweet showTweet = service.showTweet(id, fields);;

        assertEquals(tweet.getText(), showTweet.getText());
        assertNotNull(showTweet.getCoordinates());
        assertEquals(2, showTweet.getCoordinates().getCoordinates().size());
        assertEquals(tweet.getCoordinates().getCoordinates().get(0), showTweet.getCoordinates().getCoordinates().get(0));
        assertEquals(tweet.getCoordinates().getCoordinates().get(1), showTweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void deleteTweets() {
        Tweet tweet1 = TweetUtil.buildTweet("service_TweetTest" + System.currentTimeMillis(), 1d, -1d);
        Tweet tweet2 = TweetUtil.buildTweet("service_TweetTest2" + System.currentTimeMillis(), 1d, -1d);
        Tweet delTweet = service.postTweet(tweet1);
        Tweet delTweet2 = service.postTweet(tweet2);

        String [] deleteIds = new String[] {delTweet.getId_str(), delTweet2.getId_str()};
        List<Tweet> tweetList = service.deleteTweets(deleteIds);

        assertNotNull(tweetList.get(0).getCoordinates());
        assertEquals(2, tweetList.get(0).getCoordinates().getCoordinates().size());

        assertNotNull(tweetList.get(1).getCoordinates());
        assertEquals(2, tweetList.get(1).getCoordinates().getCoordinates().size());

        assertEquals(delTweet.getText(), tweetList.get(0).getText());
        assertEquals(delTweet2.getText(), tweetList.get(1).getText());
    }
}