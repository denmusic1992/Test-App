package ru.meteor.chornyy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("ReviewID")
    @Expose
    private Integer reviewID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Post")
    @Expose
    private String post;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("ReviewImageURL")
    @Expose
    private String reviewImageURL;
    @SerializedName("Content")
    @Expose
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReviewID() {
        return reviewID;
    }

    public void setReviewID(Integer reviewID) {
        this.reviewID = reviewID;
    }

    public Review withReviewID(Integer reviewID) {
        this.reviewID = reviewID;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Review withName(String name) {
        this.name = name;
        return this;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Review withPost(String post) {
        this.post = post;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Review withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getReviewImageURL() {
        return reviewImageURL;
    }

    public void setReviewImageURL(String reviewImageURL) {
        this.reviewImageURL = reviewImageURL;
    }

    public Review withReviewImageURL(String reviewImageURL) {
        this.reviewImageURL = reviewImageURL;
        return this;
    }

}
