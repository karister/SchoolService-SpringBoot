package schoolservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import schoolservice.mybatis.entity.Student;
import schoolservice.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * @author karister
 * @create 2021-07-30 15:11
 */

@RestController
@RequestMapping("/upload")
public class FileUploadController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/repair")
    String fileupload(@RequestPart("file") MultipartFile file, HttpSession session) throws IOException {
        //获取项目本地路径
        String localPath = System.getProperty("user.dir");
        //获取登录的学生信息
        Student student = (Student) session.getAttribute("student");
        //学生上传文件夹建立
        File studentDir = new File(localPath + "/upload/" + student.getId());
        if (!studentDir.exists()) {
            studentDir.mkdir();
        }
        //上传文件地址
        File uploadPath = new File(studentDir + "/" + file.getOriginalFilename());
        System.out.println("上传文件保存地址：" + uploadPath);
        //上传文件
        file.transferTo(uploadPath);
        //修改用户头像地址
        studentService.modifyProfileByID(uploadPath.toString(), student.getId());
        return "";
    }

    @PostMapping("/profile")
    public String uploadProfileImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        final String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        final String folder = "/upload/iamges/";
        final String uploadPath = staticPath + folder;
        String fileName = file.getOriginalFilename();

        if (!new File(uploadPath).exists() || new File(uploadPath).mkdirs()) {
            file.transferTo(new File(uploadPath + fileName));
        }

        String visitUrl = folder + fileName;
        updateFilePath(request, visitUrl);
        return visitUrl;
    }

    private void updateFilePath(HttpServletRequest request, String visitUrl) {
        final Student student = (Student) request.getSession().getAttribute("student");
        student.setProfile(visitUrl);
        request.setAttribute("student", student);
        studentService.modifyProfileByID(visitUrl, student.getId());
    }

    @GetMapping("profile")
    public String getProfilePath(HttpServletRequest request) {
        //获取登录的学生信息
        Student student = (Student) request.getSession().getAttribute("student");
        String profile = student.getProfile();
        System.out.println("学生头像路径为：" + profile);
        return profile;
    }

}
