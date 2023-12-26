package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description
 * 渠道类型枚举
 * @author yijie
 * @version 1.0
 * @date 2023/12/13
 */
@Getter
@AllArgsConstructor
public enum ChannelTypeEnum {

    QIAN_BAO("qianbao", "钱宝"),

    HE_LI_BAO("helibao", "合立宝"),

    JIA_LIAN("jialian","嘉联"),

            ;

    private String code;
    private String desc;

    public static ChannelTypeEnum parse(String code) {
        if (code == null) {
            throw new RuntimeException("code不能为空");
        }
        ChannelTypeEnum[] enums = ChannelTypeEnum.values();
        for (ChannelTypeEnum statusEnum : enums) {
            if (code.equals(statusEnum.getCode())) {
                return statusEnum;
            }
        }
        throw new RuntimeException("状态未定义，请检查编码:" + code);
    }

}
