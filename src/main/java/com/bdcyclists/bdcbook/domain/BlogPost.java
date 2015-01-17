package com.bdcyclists.bdcbook.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/17/15.
 */

@Entity
public class BlogPost extends BaseEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @NotEmpty
    private String content;

    @NotEmpty
    @Size(max = 1024)
    private String title;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @Override
    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}
