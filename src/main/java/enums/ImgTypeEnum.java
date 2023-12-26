package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description
 * 钱宝设备类型
 */
@Getter
@AllArgsConstructor
public enum ImgTypeEnum {

    /**
     * 法⼈身份证正⾯（注册必填）
     */
    LEGAL_PERSON_ID_POSITIVE("LEGAL_PERSON_ID_POSITIVE", "法⼈身份证正⾯"),

    /**
     * 法⼈身份证反⾯（注册必填）
     */
    LEGAL_PERSON_ID_BACK("LEGAL_PERSON_ID_BACK", "法⼈身份证反⾯"),

    /**
     * 法⼈⼿持身份证（注册65岁以上必填）
     */
    APPLICANT_WITH_ID("APPLICANT_WITH_ID", "法⼈⼿持身份证"),

    /**
     * 结算卡照⽚（注册必填）
     */
    SETTLE_CARD_IMG("SETTLE_CARD_IMG", "结算卡照⽚"),

    /**
     * 营业执照
     */
    BUSSINESS_LICENSE("BUSSINESS_LICENSE", "营业执照"),

    /**
     * ⻔店⻔头照（注册必填）
     */
    PLACE_IMG("PLACE_IMG", "⻔店⻔头照"),

    /**
     * ⻔店内景照（注册必填）
     */
    STORE_IMG("STORE_IMG", "⻔店内景照"),

    /**
     * ⻔店收银台照
     */
    CASH_SPACE_IMG("CASH_SPACE_IMG", "⻔店收银台照"),

    /**
     * 信⽤卡照⽚（注册65岁以上必填）
     */
    BANK_CARD_IMG("BANK_CARD_IMG", "信⽤卡照⽚"),

    /**
     * 持卡⼈签名（注册必填）
     */
    CARDHOLDER_SIGN("CARDHOLDER_SIGN", "持卡⼈签名"),

    /**
     * ⼈脸识别照⽚（注册必填）
     */
    OCR_FACE("OCR_FACE", "⼈脸识别照⽚"),

    ;

    final String code;
    final String name;

    /**
     * 匹配枚举值
     *
     * @param code 名称
     * @return MaterialsOperateEnum
     */
    public static ImgTypeEnum parse(String code) {
        if (code == null) {
            throw new RuntimeException("code不能为空");
        }
        ImgTypeEnum[] enums = ImgTypeEnum.values();
        for (ImgTypeEnum statusEnum : enums) {
            if (code.equals(statusEnum.getCode())) {
                return statusEnum;
            }
        }
        throw new RuntimeException("状态未定义，请检查编码:" + code);
    }
}
