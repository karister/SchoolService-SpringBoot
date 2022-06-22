package schoolservice.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
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
        if (!studentDir.exists()){
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
    public String uploadProfileImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        //获取登录的学生信息
        Student student = (Student) request.getSession().getAttribute("student");
        //获取项目classes/static的地址
        String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        String fileName = file.getOriginalFilename();  //获取文件名
        // 图片存储目录及图片名称
        String url_path = "images" + File.separator + fileName;
        //图片保存路径
        String savePath = staticPath + File.separator + url_path;
        System.out.println("图片保存地址："+savePath);
        // 访问路径=静态资源路径+文件目录路径
        String visitPath ="/" + url_path;
        System.out.println("图片访问uri："+visitPath);
        //修改用户头像地址
        studentService.modifyProfileByID(visitPath, student.getId());
        File saveFile = new File(savePath);
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
        } catch (IOException e) {
            e.printStackTrace();
        }
        return visitPath;
    }

    @GetMapping("profile")
    public String getProfilePath(HttpServletRequest request){
        //获取登录的学生信息
        Student student = (Student) request.getSession().getAttribute("student");
        String profile = student.getProfile();
        System.out.println("学生头像路径为：" + profile);
        return profile;
    }

}
