package com.poznan.put.rest.webservice.restapi.post;

import com.poznan.put.rest.webservice.restapi.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Post {

    // private fields
    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    // public constructors
    public Post(Integer id, String description, User user) {
        this.id = id;
        this.description = description;
        this.user = user;
    }
    public Post() {}

    // Getters and Setters
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
