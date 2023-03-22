package com.example.pmsauth;

import lombok.Data;

import java.util.Date;

@Data
public class UserFromTest {

    private String username;

    private String password;

    private Date createDate;
}
