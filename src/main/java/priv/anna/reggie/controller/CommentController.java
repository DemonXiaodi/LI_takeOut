package priv.anna.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import priv.anna.reggie.common.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传 下载
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommentController {

    @Value("${reggie.path}")
    private String basePath;  //文件上传后转存的路径

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){ //这里参数名必须和前端一致
        log.info(file.toString());

        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //获取后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //随机生成文件名(加上后缀)
        String fileName = UUID.randomUUID().toString()+suffix;

        //创建一个目录对象
        File dir = new File(basePath); //例：路径中是否存在\img\
        //判断当前目录是否存在
        if (!dir.exists()){
            //不存在，创建
            dir.mkdir();
        }

        //file是一个临时文件，需要转存，否则本次请求结束后文会删除
        try {
            //转存
            file.transferTo(new File(basePath+fileName)); //路径+随机文件名
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success(fileName);
    }

    /**
     * 文件下载，用于图片回显
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){

        log.info("回显文件：{}",name);

        try {

            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));
            //输出流，通过输出流将文件写回浏览器，在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){   //将读到的内容放到数组中
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            outputStream.close();
            fileInputStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
