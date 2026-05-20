package com.example.shortlink.controller;

import com.example.shortlink.entity.LinkEntity;
import com.example.shortlink.mapper.LinkMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Random;

@RestController
public class ShortLinkController {

    // 1. 声明一个日志工具，方便我们在控制台看程序到底跑到哪一步了
    private static final Logger logger = LoggerFactory.getLogger(ShortLinkController.class);

    @Autowired
    private LinkMapper linkMapper;

    /**
     * 接口一：生成短链接
     * 用法：在浏览器输入 http://localhost:9999/generate?url=https://www.baidu.com
     */
    @GetMapping("/generate")
    public String generate(@RequestParam("url") String longUrl) {
        // 定义随机字符池
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortCode = new StringBuilder();
        Random random = new Random();

        // 生成 6 位随机短码
        for (int i = 0; i < 6; i++) {
            shortCode.append(chars.charAt(random.nextInt(chars.length())));
        }

        // 把长链接和短码打包成实体对象
        LinkEntity entity = new LinkEntity();
        entity.setOriginUrl(longUrl);
        entity.setShortCode(shortCode.toString());

        // 存入数据库
        linkMapper.insert(entity);
        logger.info("【生成成功】长链接: {} -> 短码: {}", longUrl, shortCode);

        // 拼接并返回完整的短链接给用户
        return "http://localhost:9999/" + shortCode;
    }

    /**
     * 接口二：短链接跳转（最终稳定版）
     * 用法：访问刚刚生成的短链接，例如 http://localhost:9999/AbCdEf
     */
    @GetMapping("/{shortCode}")
    public void redirect(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        LinkEntity entity = linkMapper.selectByShortCode(shortCode);

        if (entity != null && entity.getOriginUrl() != null) {
            // 这次直接打印到控制台，看看到底取到了什么
            System.out.println("准备跳转到：" + entity.getOriginUrl());
            response.sendRedirect(entity.getOriginUrl());
        } else {
            // 如果还是空，返回明确的错误信息
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("错误：数据库中未找到对应的长链接！短码为：" + shortCode);
        }
    }
}