package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.Controller.TwitterController;
import ca.jrvs.apps.twitter.CrdDao.TwitterDao;
import ca.jrvs.apps.twitter.HttpHelper.HttpHelper;
import ca.jrvs.apps.twitter.HttpHelper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.Service.TwitterService;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.dao.CrdDao;

import javax.xml.ws.Service;

public class TwitterCLIApp {

    public static final String USAGE = "USAGE: TwitterCLIApp post|show|delete [options]";

    private Controller controller;

    @Autowired
    public TwitterCLIApp(Controller controller) {
        this.controller = controller;
    }
    public static void main(String[] args) {
        //Get secrets from env vars
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        //Create components and chain dependencies
        HttpHelper httpHelper = new TwitterHttpHelper(
                consumerKey, consumerSecret, accessToken, tokenSecret);
        CrdDao dao = new TwitterDao(httpHelper);
        TwitterService service = new TwitterService(dao);
        Controller controller = new TwitterController(service);
        TwitterCLIApp app = new TwitterCLIApp(controller);

        //start app
        app.run(args);
    }

    public void run(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException(USAGE);
        }
        switch (args[0]) {
            case "post":
                printTweet(controller.postTweet(args));
                break;
            case "show":
                printTweet(controller.showTweet(args));
                break;
            case "delete":
                controller.deleteTweet(args).forEach(this::printTweet);
                break;
            default:
                throw new IllegalArgumentException(USAGE);
        }
    }

    private void printTweet(Tweet tweet) {
        try {
            System.out.println(JsonParser.toJson(tweet, true, false));
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Unable to convert tweet object to string", ex);
        }
    }
}
