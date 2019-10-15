package com.zack.gojektestjava;

import java.util.List;

public class RepoModel {

    private String author;
    private String name;
    private String avatar;
    private String url;
    private String description;
    private Integer stars;
    private Integer forks;
    private Integer currentPeriodStars;
    private String language;
    private String languageColor;
    private List<UserModel> builtBy;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }

    public Integer getCurrentPeriodStars() {
        return currentPeriodStars;
    }

    public void setCurrentPeriodStars(Integer currentPeriodStars) {
        this.currentPeriodStars = currentPeriodStars;
    }

    public List<UserModel> getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(List<UserModel> builtBy) {
        this.builtBy = builtBy;
    }
}
