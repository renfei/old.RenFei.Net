package net.renfei.core.service.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import net.renfei.core.baseclass.BaseClass;
import net.renfei.core.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
public class AliyunOSS extends BaseClass {
    @Autowired
    private GlobalService globalService;
    public String upload(String path, MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RuntimeException("文件为空!");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        fileName = UUID.randomUUID().toString().replace("-", "") + suffixName; // 新文件名
        uploadFile(file.getInputStream(), path + fileName);
        return "//" + globalService.getStaticDomain() + "/" + path + fileName;
    }

    private boolean uploadFile(InputStream inputStream, String objectName) {
        // Endpoint
        String endpoint = renFeiConfig.getALIYUN_OSS_ENDPOINT();
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = renFeiConfig.getALIYUN_ACCESS_KEY_ID();
        String accessKeySecret = renFeiConfig.getALIYUN_ACCESS_KEY_SECRET();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(renFeiConfig.getALIYUN_OSS_BUCKENAME(), objectName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        return true;
    }
}
