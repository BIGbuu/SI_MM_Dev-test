package ru.buparser.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author BIG_bu
 */
@Entity
@Table(name = "twitterlenta_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TwitterLentaHistory.findAll", query = "SELECT t FROM TwitterLentaHistory t"),
    @NamedQuery(name = "TwitterLentaHistory.findByIdpk", query = "SELECT t FROM TwitterLentaHistory t WHERE t.idpk = :idpk"),
    @NamedQuery(name = "TwitterLentaHistory.findById", query = "SELECT t FROM TwitterLentaHistory t WHERE t.id = :id"),
    @NamedQuery(name = "TwitterLentaHistory.findByDate", query = "SELECT t FROM TwitterLentaHistory t WHERE t.date = :date"),
    @NamedQuery(name = "TwitterLentaHistory.findByTweets", query = "SELECT t FROM TwitterLentaHistory t WHERE t.tweets = :tweets"),
    @NamedQuery(name = "TwitterLentaHistory.findByFollowings", query = "SELECT t FROM TwitterLentaHistory t WHERE t.followings = :followings"),
    @NamedQuery(name = "TwitterLentaHistory.findByFollowers", query = "SELECT t FROM TwitterLentaHistory t WHERE t.followers = :followers"),
    @NamedQuery(name = "TwitterLentaHistory.findByParseTime", query = "SELECT t FROM TwitterLentaHistory t WHERE t.parseTime = :parseTime")})
public class TwitterLentaHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpk")
    private Integer idpk;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private int id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "tweets")
    private Integer tweets;
    @Column(name = "followings")
    private Integer followings;
    @Column(name = "followers")
    private Integer followers;
    @Column(name = "parse_time")
    private Integer parseTime;

    public TwitterLentaHistory() {
    }

    public TwitterLentaHistory(Integer idpk) {
        this.idpk = idpk;
    }

    public TwitterLentaHistory(Integer idpk, int id) {
        this.idpk = idpk;
        this.id = id;
    }

    public Integer getIdpk() {
        return idpk;
    }

    public void setIdpk(Integer idpk) {
        this.idpk = idpk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTweets() {
        return tweets;
    }

    public void setTweets(Integer tweets) {
        this.tweets = tweets;
    }

    public Integer getFollowings() {
        return followings;
    }

    public void setFollowings(Integer followings) {
        this.followings = followings;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getParseTime() {
        return parseTime;
    }

    public void setParseTime(Integer parseTime) {
        this.parseTime = parseTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpk != null ? idpk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TwitterLentaHistory)) {
            return false;
        }
        TwitterLentaHistory other = (TwitterLentaHistory) object;
        if ((this.idpk == null && other.idpk != null) || (this.idpk != null && !this.idpk.equals(other.idpk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.buparser.model.TwitterLentaHistory[ idpk=" + idpk + " ]";
    }

}
