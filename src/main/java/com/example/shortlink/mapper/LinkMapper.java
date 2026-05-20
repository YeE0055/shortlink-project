package com.example.shortlink.mapper;

import com.example.shortlink.entity.LinkEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LinkMapper {

    @Insert("INSERT INTO t_link (origin_url, short_code) VALUES (#{originUrl}, #{shortCode})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(LinkEntity link);

    // 强制指定查询结果映射到实体类
    @Select("""
        SELECT 
            id, 
            origin_url AS originUrl, 
            short_code AS shortCode, 
            create_time AS createTime 
        FROM t_link 
        WHERE short_code = #{shortCode}
    """)
    LinkEntity selectByShortCode(String shortCode);
}