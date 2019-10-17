package com.zack.gojektestjava;

import com.google.gson.Gson;
import com.zack.gojektestjava.database.RepoEntity;
import com.zack.gojektestjava.model.RepoModel;
import com.zack.gojektestjava.model.UserModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(JUnit4.class)
public class RepoModelTesting {

    @Test
    public void testToDatabaseModel() {
        RepoModel repoModel = createModel();
        Gson gson = new Gson();
        RepoEntity entity = repoModel.toDatabaseModel(gson);

        assertEquals(repoModel.getAuthor(), entity.getAuthor());
        assertEquals(repoModel.getAvatar(), entity.getAvatar());
        assertEquals(repoModel.getCurrentPeriodStars(), entity.getCurrentPeriodStars());
        assertEquals(repoModel.getDescription(), entity.getDescription());
        assertEquals(repoModel.getForks(), entity.getForks());
        assertEquals(repoModel.getLanguage(), entity.getLanguage());
        assertEquals(repoModel.getLanguageColor(), entity.getLanguageColor());
        assertEquals(repoModel.getName(), entity.getName());
        assertEquals(repoModel.getUrl(), entity.getUrl());
        assertEquals(gson.toJson(repoModel.getBuiltBy()), entity.getBuiltBy());
    }

    @Test
    public void testFromDatabaseModel() {
        RepoModel repoModel = createModel();
        Gson gson = new Gson();
        RepoEntity entity = repoModel.toDatabaseModel(gson);
        
        RepoModel newModel = new RepoModel().fromDatabaseModel(entity, gson);
        
        assertEquals(repoModel.getAuthor(), newModel.getAuthor());
        assertEquals(repoModel.getAvatar(), newModel.getAvatar());
        assertEquals(repoModel.getCurrentPeriodStars(), newModel.getCurrentPeriodStars());
        assertEquals(repoModel.getDescription(), newModel.getDescription());
        assertEquals(repoModel.getForks(), newModel.getForks());
        assertEquals(repoModel.getLanguage(), newModel.getLanguage());
        assertEquals(repoModel.getLanguageColor(), newModel.getLanguageColor());
        assertEquals(repoModel.getName(), newModel.getName());
        assertEquals(repoModel.getUrl(), newModel.getUrl());
        assertNotEquals(null, newModel.getBuiltBy());

        UserModel expectedUserModel = repoModel.getBuiltBy().get(0);
        UserModel actualUserModel = newModel.getBuiltBy().get(0);

        assertEquals(expectedUserModel.getAvatar(), actualUserModel.getAvatar());
        assertEquals(expectedUserModel.getHref(), actualUserModel.getHref());
        assertEquals(expectedUserModel.getUsername(), actualUserModel.getUsername());
    }

    private RepoModel createModel() {
        RepoModel repoModel = new RepoModel();
        repoModel.setAuthor("Michael");
        repoModel.setAvatar("123");
        repoModel.setCurrentPeriodStars(1000);
        repoModel.setDescription("1qazxsw23edcvfr4 5tb5tgbnh66");
        repoModel.setForks(123);
        repoModel.setLanguage(null);
        repoModel.setLanguageColor(null);
        repoModel.setName("Example example");
        repoModel.setUrl("https://wwww.google.com");

        UserModel userModel = new UserModel();
        userModel.setAvatar("Jacksonsa");
        userModel.setHref("heasdw");
        userModel.setUsername("youtube");

        List<UserModel> userModels = new ArrayList<>();
        userModels.add(userModel);
        repoModel.setBuiltBy(userModels);

        return repoModel;
    }
}
