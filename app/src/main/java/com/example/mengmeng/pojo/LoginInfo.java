package com.example.mengmeng.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luxiansen on 2016/9/25.
 */
public class LoginInfo implements Serializable{

    public Integer key;

    public List<User> logininfolist;
    public class User{

        public String loginPsd;
        public String loginName;
    }


}
