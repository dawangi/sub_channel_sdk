package vo;

import lombok.Data;

/**
 * description
 *
 * @author yijie
 * @version 1.0
 * @date 2023/12/21
 */
@Data
public class FileChnUploadReq {

    private String channelId;

    private String fileType;

    // fileBase不为空
    String fileBase;

    // 文件存储路径别名
    private String fileName;

}
