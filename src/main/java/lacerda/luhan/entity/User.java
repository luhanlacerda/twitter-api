package lacerda.luhan.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lacerda.luhan.utils.CustomListUserSerializer;
import lacerda.luhan.utils.CustomListTweetSerializer;

/*
Classe modelo que representa o usuario
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonSerialize(using = CustomListTweetSerializer.class)
    private List<Tweet> tweets = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_followee", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "followee_id")})
    @JsonSerialize(using = CustomListUserSerializer.class)
    private List<User> followees = new ArrayList<>();

    @ManyToMany(mappedBy = "followees")
    @JsonSerialize(using = CustomListUserSerializer.class)
    private List<User> followers = new ArrayList<>();

    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

    public User(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<User> getFollowees() {
        return followees;
    }

    public void setFollowees(List<User> followees) {
        this.followees = followees;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public void addFollowee(User user) {
        this.followees.add(user);
    }

    public void removeFollowee(User user) {
        this.followees.remove(user);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((followees == null) ? 0 : followees.hashCode());
        result = prime * result + ((followers == null) ? 0 : followers.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((tweets == null) ? 0 : tweets.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (followees == null) {
            if (other.followees != null)
                return false;
        } else if (!followees.equals(other.followees))
            return false;
        if (followers == null) {
            if (other.followers != null)
                return false;
        } else if (!followers.equals(other.followers))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (tweets == null) {
            if (other.tweets != null)
                return false;
        } else if (!tweets.equals(other.tweets))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", tweets=" + tweets + ", followees=" + followees + ", followers="
                + followers + "]";
    }

}
