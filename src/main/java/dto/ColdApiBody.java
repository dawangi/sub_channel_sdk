package dto;

import lombok.Data;

import java.io.Serializable;

/**
 * description
 *
 * @author yijie
 * @version 1.0
 * @date 2023/7/6
 */
@Data
public class ColdApiBody implements Serializable,Cloneable {
    /**
     * aes密钥
     */
    private String key;

    /**
     * 报文体【自然排序的json字符串】
     */
    private String data;

    /**
     * 签名
     */
    private String sign;

    /**
     * 请求流水号
     */
    private String logId;

    /**
     * 请求时间
     */
    private String timestamp;

    /**
     * 请求机构号
     */
    private String orgNo;

    /**
     * 请求渠道号
     */
    private String channelId;

    @Override
    public ColdApiBody clone() throws CloneNotSupportedException {
        return (ColdApiBody) super.clone();
    }
}
