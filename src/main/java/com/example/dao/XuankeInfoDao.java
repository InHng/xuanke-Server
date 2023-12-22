package com.example.dao;

import com.example.entity.XuankeInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface XuankeInfoDao extends Mapper<XuankeInfo> {
    @Select("SELECT a.*, b.name AS zhuanyeName, c.name AS teacherName, d.name AS studentName from xuanke_info AS a LEFT JOIN zhuanye_info AS b ON a.zhuanyeId = b.id LEFT JOIN teacher_info AS c ON a.teacherId = c.id LEFT JOIN student_info AS d ON a.studentId = d.id where a.name like concat('%', #{search}, '%')")
    List<XuankeInfo> findBySearch(@Param("search") String search);

    @Select("SELECT * from xuanke_info where name = #{name} and teacherId = #{teacherId} and studentId = #{studentId}")
    XuankeInfo find(@Param("name") String name, @Param("teacherId") Long teacherId, @Param("studentId") Long studentId);

    List<XuankeInfo> findByCondition(@Param("teacherId") Long teacherId, @Param("studentId") Long studentId);
}
