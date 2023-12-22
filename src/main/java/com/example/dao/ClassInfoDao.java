package com.example.dao;

import com.example.entity.ClassInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ClassInfoDao extends Mapper<ClassInfo> {
    @Select("SELECT a.*, b.name AS zhuanyeName, c.name AS teacherName from class_info AS a LEFT JOIN zhuanye_info AS b ON a.zhuanyeId = b.id LEFT JOIN teacher_info AS c ON a.teacherId = c.id where a.name like concat('%', #{search}, '%')")
    List<ClassInfo> findBySearch(@Param("search") String search);

    @Select("SELECT * from class_info where name = #{name} and teacherId = #{teacherId} limit 1")
    ClassInfo findByNameAndTeacher(@Param("name") String name, @Param("teacherId") Long teacherId);
}
