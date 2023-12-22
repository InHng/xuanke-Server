package com.example.dao;

import com.example.entity.TeacherInfo;
import com.example.entity.ZhuanyeInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ZhuanyeInfoDao extends Mapper<ZhuanyeInfo> {

    @Select("SELECT * from zhuanye_info where name = #{name}")
    TeacherInfo findByName(@Param("name") String name);

    @Select("SELECT a.*, b.name AS xueyuanName from zhuanye_info AS a LEFT JOIN xueyuan_info AS b ON a.xueyuanId = b.id where a.name like concat('%', #{search}, '%')")
    List<ZhuanyeInfo> findBySearch(@Param("search") String search);

}
