package net.renfei.core.service.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.green.model.v20180509.TextScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import net.renfei.core.config.RenFeiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AliyunGreen {
    @Autowired
    private RenFeiConfig renFeiConfig;
    /**
     * ALIYUN_ACCESS_KEY_ID和ALIYUN_ACCESS_KEY_SECRET,请替换成您自己的aliyun ak.
     * 访问regionId支持: cn-shanghai, 其他区域暂不支持, 请勿使用
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
     */
    private String ALIYUN_ACCESS_KEY_ID;
    private String ALIYUN_ACCESS_KEY_SECRET;
    private String REGION_ID;
    private IClientProfile profile;
    private IAcsClient client;

    private void setConfig() {
        ALIYUN_ACCESS_KEY_ID = renFeiConfig.getALIYUN_ACCESS_KEY_ID();
        ALIYUN_ACCESS_KEY_SECRET = renFeiConfig.getALIYUN_ACCESS_KEY_SECRET();
        REGION_ID = renFeiConfig.getGREEN_REGION_ID();
        profile = DefaultProfile.getProfile(REGION_ID, ALIYUN_ACCESS_KEY_ID, ALIYUN_ACCESS_KEY_SECRET);
        client = new DefaultAcsClient(profile);
    }

    /**
     * @param text 待检测的文本，长度不超过10000个字符
     */
    public boolean textScan(String text) {
        setConfig();
        TextScanRequest textScanRequest = new TextScanRequest();
        textScanRequest.setSysAcceptFormat(FormatType.JSON); // 指定api返回格式
        textScanRequest.setHttpContentType(FormatType.JSON);
        textScanRequest.setSysMethod(com.aliyuncs.http.MethodType.POST); // 指定请求方法
        textScanRequest.setSysEncoding("UTF-8");
        textScanRequest.setSysRegionId(REGION_ID);
        List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();
        Map<String, Object> task1 = new LinkedHashMap<String, Object>();
        task1.put("dataId", UUID.randomUUID().toString());
        /**
         * 待检测的文本，长度不超过10000个字符
         */
        task1.put("content", text);
        tasks.add(task1);
        JSONObject data = new JSONObject();

        /**
         * 检测场景，文本垃圾检测传递：antispam
         **/
        data.put("scenes", Arrays.asList("antispam"));
        data.put("tasks", tasks);
        log.info(JSON.toJSONString(data, true));
        try {
            textScanRequest.setHttpContent(data.toJSONString().getBytes("UTF-8"), "UTF-8", FormatType.JSON);
            // 请务必设置超时时间
            textScanRequest.setSysConnectTimeout(3000);
            textScanRequest.setSysReadTimeout(6000);
            HttpResponse httpResponse = client.doAction(textScanRequest);
            if (httpResponse.isSuccess()) {
                JSONObject scrResponse = JSON.parseObject(new String(httpResponse.getHttpContent(), "UTF-8"));
                if (200 == scrResponse.getInteger("code")) {
                    JSONArray taskResults = scrResponse.getJSONArray("data");
                    for (Object taskResult : taskResults) {
                        if (200 == ((JSONObject) taskResult).getInteger("code")) {
                            JSONArray sceneResults = ((JSONObject) taskResult).getJSONArray("results");
                            for (Object sceneResult : sceneResults) {
                                String scene = ((JSONObject) sceneResult).getString("scene");
                                String suggestion = ((JSONObject) sceneResult).getString("suggestion");
                                //根据scene和suggetion做相关处理
                                //suggestion == pass 未命中垃圾  suggestion == block 命中了垃圾，可以通过label字段查看命中的垃圾分类
//                                log.info("args = [" + scene + "]");
//                                log.info("args = [" + suggestion + "]");
                                return "pass".equals(suggestion.toLowerCase());
                            }
                        } else {
                            log.info("task process fail:" + ((JSONObject) taskResult).getInteger("code"));
                            return false;
                        }
                    }
                } else {
                    log.info("detect not success. code:" + scrResponse.getInteger("code"));
                    return false;
                }
            } else {
                log.info("response not success. status:" + httpResponse.getStatus());
                return false;
            }
        } catch (ServerException e) {
            log.error(e.getMessage(), e);
            return false;
        } catch (ClientException e) {
            log.error(e.getMessage(), e);
            return false;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return false;
    }
}
