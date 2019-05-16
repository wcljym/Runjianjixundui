package com.wangchenglong.graduation.contorller;

import com.wangchenglong.graduation.domain.Member;
import com.wangchenglong.graduation.repository.UserRepository;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: Graduation_WCL
 * @description: 图片上传
 * @author: Wangchenglong
 * @create: 2018-12-18 23:53
 **/
@Controller
public class FileController {
    @Autowired
    UserRepository userRepository;

    /**
     * 保存头像
     *
     * @param username
     * @param model
     * @return
     */

    @RequestMapping("/avatar")
    @ResponseBody
    public Map<String, Object> saveAvatar(@RequestParam(value = "username") String username, @RequestParam(value = "imgData") String photo, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Member member = userRepository.findMemberByUsername(username);
        System.out.println(username);
        System.out.println("111111111111111111111111111111eeee11111111111111111111");
        System.out.println(photo);
        String str = photo.substring(photo.indexOf(",") + 1);
        String imageName = java.util.UUID.randomUUID().toString();//生成图片名称
        BASE64Decoder decoder = new BASE64Decoder();
        String imgFilePath = "D:/AAAAAAAAAA/" + imageName + ".jpg";//图片地址
        File file = new File(imgFilePath);
        file.getParentFile().mkdirs();

        try {


            byte[] decoderBytes = decoder.decodeBuffer(str);
            for (int i = 0; i < decoderBytes.length; ++i) {
                if (decoderBytes[i] < 0) {// 调整异常数据
                    decoderBytes[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(file);
            out.write(decoderBytes);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = "/temp-rainy/" + imageName + ".jpg";
        member.setAvatar(filename);
        System.out.println(member.getAvatar());
        userRepository.save(member);

        map.put("status", 0);
        map.put("imgFilePath", filename);


        return map;
    }


    @RequestMapping(value = "/{username}/showImage")
    public String showImage(@PathVariable("username") String username, Model model) {
        Member member = userRepository.findMemberByUsername(username);
        String ImagePath = member.getAvatar();


        return ImagePath;
    }

    @ResponseBody
    @RequestMapping(value = "/1showImage")
    public Map<String, Object> getImage(@RequestParam(value = "username") String username) {
        Map<String, Object> map = new HashMap<>();
        Member member = userRepository.findMemberByUsername(username);
        String ImagePath = member.getAvatar();

        map.put("status", 200);
        map.put("imgFilePath", ImagePath);

        return map;
    }

    /**
     * 上传新闻缩略图
     * @param file
     * @param request
     * @param response
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/save_news_image")
    public Map<String, Object> save_news_image(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();

        if (file != null) {
            String fileName = file.getOriginalFilename();// 文件原名称
            String str = fileName.substring(fileName.indexOf(",") + 1);
            String imageName = java.util.UUID.randomUUID().toString();//生成图片名称

            String imgFilePath = "D:/AAAAAAAAAA/" + imageName + ".jpg";//图片地址
            File files = new File(imgFilePath);
            files.getParentFile().mkdirs();

            try {
                file.transferTo(files);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String filename = "/temp-rainy/" + imageName + ".jpg";
            map.put("code", 0);
            map.put("message", "success");
            map.put("src", "/temp-rainy/" + imageName + ".jpg");
        }


        return map;
    }




    @ResponseBody
    @RequestMapping(value = "/upload_resource")
    public Map<String, Object> upload_resource(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();

        if (file != null) {
            String fileName = file.getOriginalFilename();// 文件原名称
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println(suffixName);
            String filePath = "D:/AAAAAAAAAA/";
            String path = filePath + fileName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            try {
                file.transferTo(dest);// 文件写入
            } catch (IOException e) {
                e.printStackTrace();
            }

            map.put("code", 0);
            map.put("message", "success");
            map.put("src", fileName);
        }


        return map;
    }




    @ResponseBody
    @RequestMapping(value = "/download")
    public String  download(HttpServletRequest request,HttpServletResponse response) {
//        response.setCharacterEncoding(request.getCharacterEncoding());
//        response.setContentType("application/octet-stream");
//        FileInputStream fis = null;
//        try {
//            File file = new File(path1);
//            fis = new FileInputStream(file);
//            response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
//            IOUtils.copy(fis,response.getOutputStream());
//            response.flushBuffer();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        String fileName = request.getParameter("path");
        if (fileName != null) {
            File file = new File("D:\\AAAAAAAAAA\\"+fileName);
            if (file.exists()) {
                response.setContentType("application/octet-stream");
                try {
                    response.addHeader("Content-Disposition","attachment;fileName=" +new String(fileName.getBytes("UTF-8"),"iso-8859-1"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("下载成功...");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;






    }
}
