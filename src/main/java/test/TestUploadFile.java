package test;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import enums.ChannelTypeEnum;
import enums.ImgTypeEnum;
import lombok.extern.slf4j.Slf4j;
import util.HttpHelper;
import util.HttpResult;
import vo.FileChnUploadReq;

import java.io.IOException;

import static constant.CommonConstant.DEV_URL;
import static constant.RequestUrl.BASE_FILE_UPLOAD;

/**
 * @description: 测试
 * @author: shibeibei
 * @date: 2023/12/25
 */
@Slf4j
public class TestUploadFile {

    public static void main(String[] args) throws IOException {

        FileChnUploadReq request = new FileChnUploadReq();
        request.setFileBase("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");
        // 如果传入必须带后缀
        request.setFileName("测试文件名称.jpg");
        request.setFileType(ImgTypeEnum.BUSSINESS_LICENSE.getCode());
        request.setChannelId(ChannelTypeEnum.QIAN_BAO.getCode());

        HttpResult httpResult = HttpHelper.executePost(DEV_URL + BASE_FILE_UPLOAD, request, "UTF-8", 1000 * 60);
        if (HttpStatus.HTTP_OK == httpResult.getStatusCode()) {
            String content = httpResult.getContent();
            JSONObject jsonObject = JSONUtil.parseObj(content);
            log.info("返回数据", jsonObject);
        }

    }
}
