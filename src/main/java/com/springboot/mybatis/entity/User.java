package com.springboot.mybatis.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
@Data
@EqualsAndHashCode
@ToString
public class User implements Serializable {
    private String id;
    private String usercode;
    private String username;
    private String password;
    private String salt;

}
