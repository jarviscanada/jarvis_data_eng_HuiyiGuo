package ca.jrvs.apps.twitter.Service;

import ca.jrvs.apps.twitter.CrdDao.TwitterDao;
import ca.jrvs.apps.twitter.HttpHelper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ca.jrvs.apps.twitter.dao.CrdDao;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TwitterServiceUnitTest {
    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService twitterService;


    @Test
    public void postTweet(){
        when(dao.create(any())).thenReturn(new Tweet());
        Tweet newTweet = TweetUtil.buildTweet("test", 50.0, 0.0);
        Tweet postTweet = twitterService.postTweet(newTweet);

        assertNotNull(postTweet);
        assertEquals(newTweet.getText(), postTweet.getText());

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

        when(dao.findById(any())).thenReturn(new Tweet());
        Tweet showTweet = twitterService.showTweet(id, fields);
        assertNotNull(showTweet);
        assertEquals("test", showTweet.getText());
    }

    @Test
    public void deleteTweet(){
        Tweet tweet1 = TweetUtil.buildTweet("test" + System.currentTimeMillis(), 1d, -1d);
        Tweet tweet2 = TweetUtil.buildTweet("test" + System.currentTimeMillis(), 1d, -1d);
        Tweet delTweet = twitterService.postTweet(tweet1);
        Tweet delTweet2 = twitterService.postTweet(tweet2);

        when(dao.deleteById(any())).thenReturn(new Tweet());
        String [] deleteIds = new String[] {delTweet.getId_str(), delTweet2.getId_str()};
        List<Tweet> tweetList = twitterService.deleteTweets(deleteIds);

        assertEquals(tweetList.get(0).getText(), tweet1.getText());
        assertEquals(tweetList.get(1).getText(), tweet2.getText());
    }

}
