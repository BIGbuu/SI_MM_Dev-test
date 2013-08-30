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
@Table(name = "twitterlenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TwitterLenta.findAll", query = "SELECT t FROM TwitterLenta t"),
    @NamedQuery(name = "TwitterLenta.findByIdpk", query = "SELECT t FROM TwitterLenta t WHERE t.idpk = :idpk"),
    @NamedQuery(name = "TwitterLenta.findById", query = "SELECT t FROM TwitterLenta t WHERE t.id = :id"),
    @NamedQuery(name = "TwitterLenta.findByTweets", query = "SELECT t FROM TwitterLenta t WHERE t.tweets = :tweets"),
    @NamedQuery(name = "TwitterLenta.findByFollowings", query = "SELECT t FROM TwitterLenta t WHERE t.followings = :followings"),
    @NamedQuery(name = "TwitterLenta.findByFollowers", query = "SELECT t FROM TwitterLenta t WHERE t.followers = :followers"),
    @NamedQuery(name = "TwitterLenta.findByLastparse", query = "SELECT t FROM TwitterLenta t WHERE t.lastparse = :lastparse"),
    @NamedQuery(name = "TwitterLenta.findByParseTime", query = "SELECT t FROM TwitterLenta t WHERE t.parseTime = :parseTime")})
public class TwitterLenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpk")
    private Integer idpk;
    @Column(name = "id")
    private Integer id;
    @Column(name = "tweets")
    private Integer tweets;
    @Column(name = "followings")
    private Integer followings;
    @Column(name = "followers")
    private Integer followers;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lastparse")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastparse;
    @Column(name = "parse_time")
    private Integer parseTime;

    public TwitterLenta() {
    }

    public TwitterLenta(Integer idpk) {
        this.idpk = idpk;
    }

    public TwitterLenta(Integer idpk, Date lastparse) {
        this.idpk = idpk;
        this.lastparse = lastparse;
    }

    public Integer getIdpk() {
        return idpk;
    }

    public void setIdpk(Integer idpk) {
        this.idpk = idpk;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getLastparse() {
        return lastparse;
    }

    public void setLastparse(Date lastparse) {
        this.lastparse = lastparse;
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
        if (!(object instanceof TwitterLenta)) {
            return false;
        }
        TwitterLenta other = (TwitterLenta) object;
        if ((this.idpk == null && other.idpk != null) || (this.idpk != null && !this.idpk.equals(other.idpk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.buparser.model.TwitterLenta[ idpk=" + idpk + " ]";
    }

}
