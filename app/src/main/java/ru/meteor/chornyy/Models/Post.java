package ru.meteor.chornyy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("PostID")
    @Expose
    private Integer postID;
    @SerializedName("Created")
    @Expose
    private String created;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Author")
    @Expose
    private String author;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("PostImageURL")
    @Expose
    private String postImageURL;
    @SerializedName("Content")
    @Expose
    private String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public Post withPostID(Integer postID) {
        this.postID = postID;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Post withCreated(String created) {
        this.created = created;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Post withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Post withAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Post withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPostImageURL() {
        return postImageURL;
    }

    public void setPostImageURL(String postImageURL) {
        this.postImageURL = postImageURL;
    }

    public Post withPostImageURL(String postImageURL) {
        this.postImageURL = postImageURL;
        return this;
    }
}
