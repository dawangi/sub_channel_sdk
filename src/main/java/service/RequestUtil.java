package service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import dto.ColdApiBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ColdApiBodyUtil;
import util.HttpHelper;
import util.HttpResult;

/**
 * @description: 请求公共接口
 * @author: shibeibei
 * @date: 2023/12/25
 */
public class RequestUtil {

    private static Logger logger = LoggerFactory.getLogger(RequestUtil.class);


    /**
     * 请求渠道
     * */
    public static ColdApiBody request(ColdApiBody request, String requestUrl, String chnPublicKey, String privateKey) {
        try {
            String orgNo = request.getOrgNo();
            String channelId = request.getChannelId();
            String data = request.getData();
            request.setLogId (IdUtil.getSnowflake(1, 1).nextIdStr());
            request.setTimestamp (String.valueOf (System.currentTimeMillis ()));
            logger.info("【请求机构:{},渠道:{},地址:{}】", orgNo, channelId, requestUrl);
            logger.info("【请求参数加密前】:{}", data);

            // 处理请求数据
            request = ColdApiBodyUtil.encrypt(request, privateKey, chnPublicKey);

            // 发起请求
            HttpResult result = HttpHelper.executePost(requestUrl, request, "UTF-8", 1000 * 60);
            logger.info("【返回数据】:{}", result);
            return JSONObject.parseObject(result.getContent(), ColdApiBody.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("调用渠道请求方法失败: "+e.getMessage());
        }
    }

    /**
     * 解析返回的报文
     * */
    public static ColdApiBody response(ColdApiBody response, String chnPublicKey, String privateKey) {
        try {

            // 解密返回的数据
            response = ColdApiBodyUtil.decrypt(response, privateKey, chnPublicKey);

            logger.info("【返回解密数据】:{}", response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("调用渠道解密方法失败: "+e.getMessage());
        }
    }
}
