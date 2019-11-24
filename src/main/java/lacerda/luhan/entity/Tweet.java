package lacerda.luhan.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
Classe modelo que representa o tweet
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = true, updatable = true)
    private User user;

    @Temporal(TemporalType.DATE)
    private Date date;

    public Tweet() {
        super();
        this.user = new User();
        this.date = new Date();
    }

    public Tweet(String message, User user, Date date) {
        this.message = message;
        this.user = user;
        this.date = date;
    }

    public Tweet(String message, User user) {
        this.message = message;
        this.user = user;
        this.date = new Date();
    }

    public Tweet(String message) {
        this.message = message;
        this.date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
