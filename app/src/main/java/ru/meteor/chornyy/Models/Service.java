package ru.meteor.chornyy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {

    @SerializedName("ServiceID")
    @Expose
    private Integer serviceID;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Content")
    @Expose
    private String content;
    @SerializedName("ServiceImageURL")
    @Expose
    private String serviceImageURL;

    public Integer getServiceID() {
        return serviceID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public Service withServiceID(Integer serviceID) {
        this.serviceID = serviceID;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Service withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Service withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Service withContent(String content) {
        this.content = content;
        return this;
    }

    public String getServiceImageURL() {
        return serviceImageURL;
    }

    public void setServiceImageURL(String serviceImageURL) {
        this.serviceImageURL = serviceImageURL;
    }

    public Service withServiceImageURL(String serviceImageURL) {
        this.serviceImageURL = serviceImageURL;
        return this;
    }

}
