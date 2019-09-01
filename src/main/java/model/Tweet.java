package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tweet")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.ALL) // wiele tweetów jeden autor
    @JoinColumn(name = "id_author")
    private User author;
    @Column(name = "published_at") // data publikacji tweeta
    private Date publishedAt;
    @Column(name = "message") // message
    private String message;

// zawsze pamiętaj o pojo
    public Tweet() {
    }
//oraz o akcesoriach
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
