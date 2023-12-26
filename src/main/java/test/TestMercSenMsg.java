package test;

import cn.hutool.json.JSONUtil;
import dto.ColdApiBody;
import enums.ChannelTypeEnum;
import service.RequestUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static constant.CommonConstant.DEV_URL;
import static constant.RequestUrl.SEND_MSG;

/**
 * @description: 测试
 * @author: shibeibei
 * @date: 2023/12/25
 */
public class TestMercSenMsg {

    public void sendMsgRequest(){

        ColdApiBody request = new ColdApiBody();
        request.setOrgNo("10001");
        request.setChannelId(ChannelTypeEnum.QIAN_BAO.getCode());
        Map<String, Object> param = new HashMap<>();
        param.put("usrOprMbl", "18735794521");
        request.setData(JSONUtil.toJsonStr(param));

        ColdApiBody response = RequestUtil.request(request, DEV_URL + SEND_MSG, "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");
        System.out.println("返回数据:"+ response);

    }

    public void sendMsgResponse(){

        ColdApiBody response = new ColdApiBody();
        response.setOrgNo("10001");
        response.setChannelId(ChannelTypeEnum.QIAN_BAO.getCode());
        response.setData("");

        ColdApiBody coldApiBody = RequestUtil.response(response, "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");
        System.out.println("返回数据:"+ coldApiBody.getData());
    }

    public static void main(String[] args) throws IOException {
        TestMercSenMsg request = new TestMercSenMsg();
        request.sendMsgRequest();

        request.sendMsgResponse();
    }
}
