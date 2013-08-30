package ru.buparser.beans;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import twitter4j.IDs;
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
    private int freindsIdsCount;
     
    
    public TwitterBean(){
        ConfigurationBuilder cb = new ConfigurationBuilder();     
        cb.setDebugEnabled(Boolean.getBoolean(ResourceBundle.getBundle("/OAuth").getString("debug")))
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
    public int getFreindsIdsCount() throws TwitterException{
        long cursor = -1;
        IDs freindsIds;

        freindsIdsCount=0;
        do {
            freindsIds = twitter.getFriendsIDs(ResourceBundle.getBundle("/Twitter").getString("user"),cursor);
            freindsIdsCount += freindsIds.getIDs().length;
        }while ( (cursor = freindsIds.getNextCursor()) !=0 );
        
        return freindsIdsCount;
    }
    public void 
}
