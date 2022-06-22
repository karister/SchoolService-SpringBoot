package schoolservice.mybatis.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import schoolservice.mybatis.entity.Student;

/**
 * @author karister
 * @create 2021-07-29 3:17
 */
@Mapper
public interface StudentMapper {
    //根据学号查询学生信息
    Student getStuInfo(String id);

    //增加学生
    void addStu(Student student);

    //修改用户名为学号
    void modifyStuid(Student student);

    //根据用户名查找学生
    Student queryStuByName(String username);

    //根据学号查找学生
    Student queryStuByStuID(String id);

    //修改用户头像
    void modifyProfileByID(@Param("profilePath") String profilePath, @Param("stuid") String id);



}
