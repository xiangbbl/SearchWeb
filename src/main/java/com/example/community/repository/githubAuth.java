package com.example.community.repository;

import com.alibaba.fastjson.JSON;
import com.example.community.model.GithubUser;
import com.example.community.model.Token;
import okhttp3.*;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class githubAuth {

    //Step two
    public String getToken(Token token){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(token));
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            String split[] = s.split("&");
            String t = split[0].split("=")[1];
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Step three
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            System.out.println(s);
            GithubUser githubUser = JSON.parseObject(s, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
