package schoolservice.service.Impl;



import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schoolservice.mybatis.mapper.StudentMapper;
import schoolservice.mybatis.entity.Student;
import schoolservice.service.StudentService;

/**
 * @author karister
 * @create 2021-07-29 3:21
 */
@Service
public class StudentServiceImpl implements StudentService {

    private StudentMapper studentMapper;

    @Autowired
    public void setStudent(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public Student getStuInfo(String stuid) {
        return studentMapper.getStuInfo(stuid);
    }

    @Override
    public void addStu(Student student) {
        studentMapper.addStu(student);
        this.modifyStuid(student);
    }

    @Override
    public void modifyStuid(Student student) {
        studentMapper.modifyStuid(student);
    }

    @Override
    public Student queryStuByName(String username) {
        return studentMapper.queryStuByName(username);
    }

    @Override
    public Student queryStuByStuID(String stuid) {
        return studentMapper.queryStuByName(stuid);
    }

    @Override
    public void modifyProfileByID(String profilePath, String id) {
        System.out.println("用户：" + id + "头像已修改");
        System.out.println("路径:" + profilePath);
        studentMapper.modifyProfileByID(profilePath,id);
    }
}


