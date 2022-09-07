package ca.jrvs.apps.twitter.example;

import com.google.gdata.util.common.base.PercentEscaper;
import jdk.jfr.Percentage;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.Arrays;

public class TwitterApiTest {

    private static String CONSUMER_KEY = "sy3DVFML7wAzOzCVj0bh6DG5w";
            //System.getenv( "consumerKey");
    private static String CONSUMER_SECRET = "6IrhjDyojKnpUKeSGPw7fxm8WcqkOMW5SM1pkt4clbphVmwTQ7";
                    //System.getenv("consumerSecret");
    private static String ACCESS_TOKEN = "796582358023475200-LRZMG6jpPXXNAJPDFJwEmyVDVU3p4nL";
                            //System.getenv("accessToken");
    private static String TOKEN_SECRET = "An3evEuzmWH6g88iWRgLjYQxvQsoSlDV34qRHpuyT3rAH";

                                    //System.getenv("tokenSecret");

    public static void main(String[] args) throws Exception {

        //setup oauth
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN,TOKEN_SECRET);

        //create an HTTP GET request
        String status = "today is a good day";
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        HttpPost request = new HttpPost(
                "https://api.twitter.com/1.1/statuses/update.json?status=" + percentEscaper.escape(status));

        //sign the request (add headers)
        consumer.sign(request);

        System.out.println("HTTP Request Headers:");
        Arrays.stream(request.getAllHeaders()).forEach(System.out::println);

        //send the request
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}