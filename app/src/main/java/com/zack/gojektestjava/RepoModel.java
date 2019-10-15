package com.zack.gojektestjava;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zack.gojektestjava.database.RepoEntity;

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

    public RepoModel fromDatabaseModel(RepoEntity entity, Gson gson) {
        this.author = entity.getAuthor();
        this.avatar = entity.getAvatar();
        this.currentPeriodStars = entity.getCurrentPeriodStars();
        this.description = entity.getDescription();
        this.forks = entity.getForks();
        this.language = entity.getLanguage();
        this.languageColor = entity.getLanguageColor();
        this.name = entity.getName();
        this.stars = entity.getStars();
        this.url = entity.getUrl();
        if (!TextUtils.isEmpty(entity.getBuiltBy())) {
            this.builtBy = gson.fromJson(entity.getBuiltBy(), new TypeToken<List<UserModel>>(){}.getType());
        }
        return this;
    }

    public RepoEntity toDatabaseModel(Gson gson) {
        RepoEntity entity = new RepoEntity();
        entity.setAuthor(author);
        entity.setAvatar(avatar);
        entity.setCurrentPeriodStars(currentPeriodStars);
        entity.setDescription(description);
        entity.setForks(forks);
        entity.setLanguage(language);
        entity.setLanguageColor(languageColor);
        entity.setName(name);
        entity.setStars(stars);
        entity.setUrl(url);
        entity.setBuiltBy(gson.toJson(builtBy));
        return entity;
    }
}
