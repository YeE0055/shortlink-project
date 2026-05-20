package com.example.shortlink.entity;

import lombok.Data;
import java.util.Date;

@Data
public class LinkEntity {
    private Long id;

    // 重点：确保这里的名字和数据库里的 origin_url 对应
    private String originUrl;

    private String shortCode;
    private Date createTime;
}