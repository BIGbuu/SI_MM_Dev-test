package ru.buparser.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author BIG_bu
 */
@Entity
@Table(name = "twitterlenta_tweets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TwitterLentaTweets.findAll", query = "SELECT t FROM TwitterLentaTweets t"),
    @NamedQuery(name = "TwitterLentaTweets.findById", query = "SELECT t FROM TwitterLentaTweets t WHERE t.id = :id"),
    @NamedQuery(name = "TwitterLentaTweets.findByUserId", query = "SELECT t FROM TwitterLentaTweets t WHERE t.userId = :userId"),
    @NamedQuery(name = "TwitterLentaTweets.findByText", query = "SELECT t FROM TwitterLentaTweets t WHERE t.text = :text"),
    @NamedQuery(name = "TwitterLentaTweets.findByRetweetCount", query = "SELECT t FROM TwitterLentaTweets t WHERE t.retweetCount = :retweetCount"),
    @NamedQuery(name = "TwitterLentaTweets.findByCreatedAt", query = "SELECT t FROM TwitterLentaTweets t WHERE t.createdAt = :createdAt")})
public class TwitterLentaTweets implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Size(max = 300)
    @Column(name = "text")
    private String text;
    @Column(name = "retweet_count")
    private Integer retweetCount;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public TwitterLentaTweets() {
    }

    public TwitterLentaTweets(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(Integer retweetCount) {
        this.retweetCount = retweetCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TwitterLentaTweets)) {
            return false;
        }
        TwitterLentaTweets other = (TwitterLentaTweets) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.buparser.model.TwitterLentaTweets[ id=" + id + " ]";
    }

}
