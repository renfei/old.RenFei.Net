package net.renfei.web.controller.api;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.service.aliyun.AliyunOSS;
import net.renfei.web.baseclass.BaseRestController;
import net.renfei.web.entity.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/upload")
public class UploadController extends BaseRestController {
    @Autowired
    private AliyunOSS aliyunOSS;

    @PostMapping("image")
    public APIResult uploadImg(MultipartFile file) {
        if (file.isEmpty()) {
            return APIResult.fillResult(false, "文件为空!");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        if (".jpg".equals(suffixName.toLowerCase()) ||
                ".png".equals(suffixName.toLowerCase()) ||
                ".gif".equals(suffixName.toLowerCase()) ||
                ".svg".equals(suffixName.toLowerCase()) ||
                ".jpeg".equals(suffixName.toLowerCase())
        ) {
            String filePath = "upload/image/" + dateUtil.getYear() + "/"; // 上传后的路径
            try {
                String url = aliyunOSS.upload(filePath, file);
                return APIResult.fillResult(true, url, url);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                return APIResult.fillResult(false, ex.getMessage());
            }
        } else {
            return APIResult.fillResult(false, "文件类型不允许");
        }

    }

    @PostMapping("file")
    public APIResult uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return APIResult.fillResult(false, "文件为空!");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "upload/image/" + dateUtil.getYear() + "/"; // 上传后的路径
        try {
            String url = aliyunOSS.upload(filePath, file);
            return APIResult.fillResult(true, url, url);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return APIResult.fillResult(false, ex.getMessage());
        }
    }
}
