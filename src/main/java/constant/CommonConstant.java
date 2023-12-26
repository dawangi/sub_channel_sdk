package constant;

/**
 * 接口请求
 **/
public interface CommonConstant {

    /**
     * 测试环境接口请求url
     * */
    String LOCAL_URL = "http://127.0.0.1:8700";

    /**
     * 测试环境接口请求url
     * */
    String DEV_URL = "http://58.34.44.125:12120";

    /**
     * 生产环境请求url
     * */
    String PRO_URL = "";

    String GET_ORGNO_METHOD="getOrgNo";

    String GET_CHANNELID_METHOD="getChannelId";

    String TOJSON_METHOD="toJson";

    String GET_CHNPUBLICKEY_METHOD="getChnPublicKey";

    String GET_PRIVATEKEY_METHOD="getPrivateKey";

}
