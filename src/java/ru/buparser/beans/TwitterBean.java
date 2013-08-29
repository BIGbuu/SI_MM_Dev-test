package ru.buparser.beans;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;


@ManagedBean
@RequestScoped
public class TwitterBean implements Serializable{
    private Twitter twitter;
    private ResponseList<Status> statuses;
    private User user;
    
    public TwitterBean(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
/*        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("Yh9GRSek5AkcdvnCF1KrBQ")
          .setOAuthConsumerSecret("XzvaRrHdsitHGsoDmuKIDcHIwTiLxvn8t87Wha0VNuA")
          .setOAuthAccessToken("42683349-TWQZG5AoJN8iwt2mxmqCKa7kJRudPpcy4IW9gScU")
          .setOAuthAccessTokenSecret("85Szks5ece3a9LAe2Ut2vTaauxeyRUDfjq0qrascAs");
*/        
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(ResourceBundle.getBundle("/OAuth").getString("consumerKey"))
          .setOAuthConsumerSecret(ResourceBundle.getBundle("/OAuth").getString("consumerSecret"))
          .setOAuthAccessToken(ResourceBundle.getBundle("/OAuth").getString("accessToken"))
          .setOAuthAccessTokenSecret(ResourceBundle.getBundle("/OAuth").getString("accessTokenSecret"));
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();    
        
    }
    
    public List<Status> getStatuses() throws TwitterException{
        statuses = twitter.getUserTimeline(ResourceBundle.getBundle("/Twitter").getString("user"));

        return statuses;
    }
    
    public User getUser() throws TwitterException{
        user = twitter.showUser(ResourceBundle.getBundle("/Twitter").getString("user"));
        
        return user;
    }
}
