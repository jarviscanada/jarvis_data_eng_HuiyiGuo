package ca.jrvs.apps.twitter.Service;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.dao.CrdDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwitterService implements Service {

    private CrdDao dao;

    @Autowired
    public TwitterService(CrdDao dao){
        this.dao = dao;
    }

    @Override
    public Tweet postTweet(Tweet tweet) {
        //twitter sring <= 140
        //geocordinate max 90, min -90
        final Integer maxStringLength = 140;
        final Integer maxGeoCoordinate = 90;
        final Integer minGeoCoordinate = -90;

        String textBody = tweet.getText();
        Integer longitude = tweet.getCoordinates().getCoordinates().get(0);
        Integer latitude = tweet.getCoordinates().getCoordinates().get(1);

        if(textBody.length() > maxStringLength){
            throw new IllegalArgumentException("Tweet text need to be less than 140 characters");
        }else if(longitude < minGeoCoordinate || longitude > maxGeoCoordinate){
            throw new IllegalArgumentException("Longitude is out of range");
        }else if(latitude < minGeoCoordinate || latitude > maxGeoCoordinate){
            throw new IllegalArgumentException("Latitude is out of range");
        }
        return (Tweet) dao.create(tweet);
    }

    @Override
    public Tweet showTweet(String id, String[] fields) {
        if (id == null){
            throw new IllegalArgumentException("empty id");
        }else if (!id.matches("[0-9]+")){
            throw new IllegalArgumentException("id contains only numbers");
        }

        return (Tweet) dao.findById(id);
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        List<Tweet> deleteTweet = new ArrayList<>();

        if (ids == null){
            throw new IllegalArgumentException("empty id");
        }
        for (String id: ids){
            if (!id.matches("[0-9]+")){
                throw new IllegalArgumentException("id contains only numbers");
            }else {
                deleteTweet.add((Tweet) dao.deleteById(id));
            }

        }
        return deleteTweet;
    }
}
