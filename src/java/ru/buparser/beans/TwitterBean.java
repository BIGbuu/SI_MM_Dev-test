package ru.buparser.beans;

import java.io.Serializable;
import java.sql.*;
import javax.sql.*;
import javax.ejb.*;
import javax.naming.*;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
    private Context ctx;
    private DataSource ds; 
    
    public TwitterBean() throws TwitterException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NamingException{
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

        ctx = new InitialContext();
        ds = (DataSource)ctx.lookup("twitter");        
        
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
        String insertUser1 = ""
                 + " INSERT INTO twitterlenta_history (id, date, tweets, followings, followers, parse_time)"
                 + " SELECT id, lastparse, tweets, followings, followers, parse_time FROM twitterlenta WHERE id= ? ";
        String insertUser2 = ""
                 + " INSERT INTO twitterlenta (id,tweets,followings,followers,parse_time)"
                 + " VALUES ( ? , ? , ? , ? , ?)"
                 + " ON DUPLICATE KEY UPDATE "
                 + " tweets    = ? ,"
                 + " followings= ? ,"
                 + " followers = ?,"
                 + " parse_time= ? ";

        Connection conn = null;
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;

        try {
            conn = ds.getConnection(ResourceBundle.getBundle("/dbconnect").getString("user"), ResourceBundle.getBundle("/dbconnect").getString("password"));
            conn.setAutoCommit(false);
            pst1 = conn.prepareStatement(insertUser1);
                pst1.setLong(1, user.getId());
                pst1.executeUpdate();
            pst2 = conn.prepareStatement(insertUser2);
                pst2.setLong(1, user.getId());
                pst2.setInt( 2, user.getStatusesCount());
                pst2.setInt( 3, freindsIdsCount);
                pst2.setInt( 4, user.getFollowersCount());
                pst2.setLong(5, (System.currentTimeMillis()-startParseTime) );
                pst2.setInt( 6, user.getStatusesCount());
                pst2.setInt( 7, freindsIdsCount);
                pst2.setInt( 8, user.getFollowersCount());
                pst2.setLong(9, (System.currentTimeMillis()-startParseTime) );
                pst2.executeUpdate();
                conn.commit();
                
        } finally {
            if (pst1 != null) { pst1.close(); }
            if (pst2 != null) { pst2.close(); }
            if (conn != null) { conn.close(); }
        }
            
    }
}
