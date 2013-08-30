package ru.buparser.beans;

import java.io.Serializable;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NamedQueries;
import org.hibernate.Query;
import ru.buparser.HibernateUtil;
import org.hibernate.Session;
import twitter4j.IDs;
import twitter4j.Paging;
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
    private long startParseTime;
     
    
    public TwitterBean() throws TwitterException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        startParseTime = System.currentTimeMillis();
        ConfigurationBuilder cb = new ConfigurationBuilder();     
        cb.setDebugEnabled(Boolean.getBoolean(ResourceBundle.getBundle("/OAuth").getString("debug")))
          .setOAuthConsumerKey(ResourceBundle.getBundle("/OAuth").getString("consumerKey"))
          .setOAuthConsumerSecret(ResourceBundle.getBundle("/OAuth").getString("consumerSecret"))
          .setOAuthAccessToken(ResourceBundle.getBundle("/OAuth").getString("accessToken"))
          .setOAuthAccessTokenSecret(ResourceBundle.getBundle("/OAuth").getString("accessTokenSecret"));
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();    
        
        loadUser();
        loadFriendsIds();
        loadStatuses();
        dbInsertUserInfo();
    }
    
    public List<Status> getStatuses() throws TwitterException{
        
        return statuses;
    }
    
    public User getUser() throws TwitterException{

        return user;
    }
    public int getFreindsIdsCount() throws TwitterException{
        
        return freindsIdsCount;
    }
    private void loadUser() throws TwitterException{
        user = twitter.showUser(ResourceBundle.getBundle("/Twitter").getString("user"));
        
    }
    private void loadStatuses() throws TwitterException{
        statuses = twitter.getUserTimeline(ResourceBundle.getBundle("/Twitter").getString("user"), new Paging(1, 25));
        
    }
    private void loadFriendsIds() throws TwitterException{
        long cursor = -1;
        IDs freindsIds;

        freindsIdsCount=0;
        do {
            freindsIds = twitter.getFriendsIDs(ResourceBundle.getBundle("/Twitter").getString("user"),cursor);
            freindsIdsCount += freindsIds.getIDs().length;
        }while ( (cursor = freindsIds.getNextCursor()) !=0 );
        
    }
    private void dbInsertUserInfo() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        try (Connection connection = DriverManager.getConnection(ResourceBundle.getBundle("/dbconnect").getString("db"),ResourceBundle.getBundle("/dbconnect").getString("user"),ResourceBundle.getBundle("/dbconnect").getString("password"))) {
            Statement statement = connection.createStatement();
            
            statement.execute(""
                 + " INSERT INTO twitterlenta_history (id, date, tweets, followings, followers, parse_time)"
                 + " SELECT id, lastparse, tweets, followings, followers, parse_time FROM twitterlenta WHERE id="+ user.getId() );
            statement.execute(""
                 + " INSERT INTO twitterlenta (id,tweets,followings,followers,parse_time)"
                 + " VALUES ("
                 + user.getId()            + ","
                 + user.getStatusesCount() + ","
                 + freindsIdsCount         + ","
                 + user.getFollowersCount()+ ","
                 + ((System.currentTimeMillis()-startParseTime)) + ")"
                 + " ON DUPLICATE KEY UPDATE "
                 + " tweets="     + user.getStatusesCount()  + ","
                 + " followings=" + freindsIdsCount          + ","
                 + " followers="  + user.getFollowersCount() + ","
                 + " parse_time=" + ((System.currentTimeMillis()-startParseTime))
            );
            statement.close();
        }        
    }
}
