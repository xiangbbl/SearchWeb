package com.example.community.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
