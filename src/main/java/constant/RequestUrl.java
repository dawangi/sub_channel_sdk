package constant;

/**
 * 接口请求
 **/
public interface RequestUrl {

    /**
     * base64文件上传
     * */
    String BASE_FILE_UPLOAD = "/core/file/uploadFileBase";

    /**
     * 商户短信发送
     * */
    String SEND_MSG="/cold-open-business/channel/merc/send";

    /**
     * 商户入网短信验证
     * */
    String CHECK_SEND_MSG="/cold-open-business/channel/merc/send/result";

    /**
     * 商户人脸token获取
     * */
    String FACE_TOKEN="/cold-open-business/channel/merc/faceToken";

    /**
     * 商户人脸信息获取
     * */
    String CHECK_FACE_TOKEN="/cold-open-business/channel/merc/faceToken/result";

    /**
     * 商户注册
     * */
    String MERC_REGISTER="/cold-open-business/channel/merc/register";

    /**
     * 商户注册查询
     * */
   String MERC_REGISTER_RESULT="/cold-open-business/channel/merc/register/result";

    /**
     * 终端绑定
     * */
   String TERM_BIND="/cold-open-business/sub/term/operate/bind";

    /**
     * 终端解绑
     * */
    String TERM_UN_BIND="/cold-open-business/sub/term/operate/unBind";

    /**
     * 终端设置押金/流量卡费
     * */
    String TERM_STOP_PAY_APPLY="/cold-open-business/sub/term/stopPay/apply";

    /**
     * 终端押金/流量卡费设置结果查询
     * */
    String TERM_STOP_PAY_QUERY="/cold-open-business/sub/term/stopPay/query";

}
