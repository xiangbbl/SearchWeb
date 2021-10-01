package com.example.community.controller;

import com.example.community.entity.User;
import com.example.community.mapper.UserMapper;
import com.example.community.model.GithubUser;
import com.example.community.model.Token;
import com.example.community.repository.UserRep;
import com.example.community.repository.githubAuth;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Controller
public class authController {
    @Autowired
    private githubAuth githubAuth;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRep userRep;

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
            String t;
            User user = new User();
            //User user = user1;

            // Not exist
            //System.out.println("check if ID is exist: " + githubUser.getId());
            //TODO: githubUser.getId() return account id. Check account id in user class
            //userRep.findByName
            boolean b = existsByAccount(String.valueOf(githubUser.getId()));
            System.out.println("boolean: "+  b);
            if(!b) {
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setName(githubUser.getName());
                user.setGmtModified(user.getGmtCreate());
                user.setAvatarUrl(githubUser.getAvatarUrl());

                t = set_Token(user);
                userMapper.insert(user);
                response.addCookie(new Cookie("token", t));
            }
            else{
                User old_user = userRep.findByaccountId(String.valueOf(githubUser.getId()));
                //old_user.setToken(set_Token(user));
                System.out.println("old token" + old_user.getToken());
                t = set_Token(old_user);
                System.out.println("new token" + t);
                userRep.save(old_user);
                response.addCookie(new Cookie("token", t));
                //updateToken(user);
            }
            // Set cookies

            return "redirect:/";
        }
        else{
            return "redirect:/";
        }
    }

    public String set_Token(User user){
        String t = UUID.randomUUID().toString();
        user.setToken(t);
        return t;
    }

    public void updateToken(User user){
        User old_user = userRep.findById(user.getId()).orElse(null);
        old_user.setToken(set_Token(user));
    }

    public boolean existsByAccount(String accountId){
        boolean b = userRep.existsByaccountId(accountId);

        return b;
    }

    public User findByaccountId(String accountId){
        return userRep.findByaccountId(accountId);
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse){
        httpServletRequest.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
        return "redirect:/";
    }
}
