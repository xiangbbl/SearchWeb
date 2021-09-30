package com.example.community.controller;

import com.example.community.entity.User;
import com.example.community.mapper.UserMapper;
import com.example.community.model.GithubUser;
import com.example.community.model.Token;
import com.example.community.repository.githubAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class authController {
    @Autowired
    private githubAuth githubAuth;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client_id}")
    private String ClientId;
    @Value("${github.client_secret}")
    private String Client_secret;
    @Value("${github.Redirect_uri}")
    private String Redirect_uri;

    //Step one Request a user's GitHub identity
    @GetMapping("/callback")
    public String index(@RequestParam("code") String code,
                        @RequestParam("state") String state,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        Token token = new Token();
        token.setClient_id(ClientId);
        token.setClient_secret(Client_secret);
        token.setRedirect_uri(Redirect_uri);
        token.setCode(code);
        token.setState(state);
        String access_token = githubAuth.getToken(token);
        GithubUser githubUser = githubAuth.getUser(access_token);

        if(githubUser != null){
            User user1 = new User();
            User user = user1;
            String t = UUID.randomUUID().toString();
            System.out.println(t);
            user.setToken(t);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setName(githubUser.getName());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userMapper.insert(user);
            // Set cookies
            response.addCookie(new Cookie("token", t));
            return "redirect:/";
        }
        else{
            return "redirect:/";
        }
    }
}
