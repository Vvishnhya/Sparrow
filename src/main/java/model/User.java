package model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_registration")
    private Date dateOfRegistration;

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "follows_followed",
            joinColumns = {@JoinColumn(name = "follows_id")},
            inverseJoinColumns = {@JoinColumn(name = "followed_id")})
    private Set<User> follows = new HashSet<>();

    @ManyToMany(mappedBy = "follows")
    private Set<User>  followed = new HashSet<>();
    public User() { // wymagany pojo (plain object java)

    }

    public User(String name, String lastname, String email, Date dateOfRegistration, String password) {
        this.login = login;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.dateOfRegistration = dateOfRegistration;
        this.password = password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public String getPassword() {
        return password;
    }

    public Set<User> getFollows() {
        return follows;
    }

    public void setFollows(Set<User> follows) {
        this.follows = follows;
    }

    public Set<User> getFollowed() {
        return followed;
    }

    public void setFollowed(Set<User> followed) {
        this.followed = followed;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", dateOfregistration=" + dateOfRegistration +
                ", password='" + password + '\'' +
                '}';
    }
}
