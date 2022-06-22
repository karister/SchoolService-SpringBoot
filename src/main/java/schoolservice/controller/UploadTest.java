package schoolservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author karister
 * @create 2021-10-18 9:43
 */
@Controller
public class UploadTest {

    /**
     * 上传文件到项目的静态文件目录
     * @param file
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadImage")
    public String uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

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

}
