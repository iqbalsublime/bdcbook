package com.bdcyclists.bdcbook.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/17/15.
 */
@Entity
public class Blog extends BaseEntity<Long> {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Size(max = 120)
    private String blogName;

    @Size(max = 200)
    private String tagLine; //optional

    @OneToOne(mappedBy = "blog")
    private User owner;

    @OneToMany(mappedBy = "blog")
    private Set<BlogPost> blogPosts = new HashSet<>();

    @Override
    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(Set<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }
}
