package ca.jrvs.apps.twitter.Controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TwitterController implements Controller {

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private Service service;

    @Autowired
    public TwitterController(Service service){
        this.service = service;
    }

    @Override
    public Tweet postTweet(String[] args) {
        if (args.length != 3){
            throw new IllegalArgumentException("USAGE: TwitterCLIAPP post \"tweet_text\" \"latitude:longitude\"");
        }

        String tweet_txt = args[1];
        String coord = args[2];
        String[] coordArray = coord.split(COORD_SEP);
        if (coordArray.length != 2 || tweet_txt.isEmpty()){
            throw new IllegalArgumentException("Invalid location format\nUSAGE: TwitterCLIAPP post \"tweet_text\" \"latitude:longitude\"");
        }

        Double lon = null;
        Double lat = null;

        try {
            lat = Double.parseDouble(coordArray[0]);
            lon = Double.parseDouble(coordArray[1]);
        }catch (Exception e){
            throw new IllegalArgumentException("Invalid location format\nUSAGE: TwitterCLIAPP post \"tweet_text\" \"latitude:longitude\"", e);
        }

        Tweet postTweet = TweetUtil.buildTweet(tweet_txt, lon, lat);
        return service.postTweet(postTweet);
    }

    @Override
    public Tweet showTweet(String[] args) {
        if (args.length < 2){
            throw new IllegalArgumentException("USAGE: TwitterCLIAPP show \"id...\"");
        }

        String id = args[1];

        String[] fields = args.length > 2 ? args[2].split(COMMA) : null;

        return service.showTweet(id, fields);
    }

    @Override
    public List<Tweet> deleteTweet(String[] args) {
        if (args.length < 2){
            throw new IllegalArgumentException("USAGE: TwitterCLIAPP delete \"id...\"");
        }
        String[] fields = args[1].split(COMMA);
        return service.deleteTweets(fields);
    }
}
