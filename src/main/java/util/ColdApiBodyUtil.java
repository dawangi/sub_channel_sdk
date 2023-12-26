package util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.json.JSONUtil;
import dto.ColdApiBody;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;
import java.util.TreeMap;


/**
 * description
 * API BODY 工具类
 * @author yijie
 * @version 1.0
 * @date 2023/7/6
 */
@Slf4j
public class ColdApiBodyUtil {

    /**
     * AES解密
     * @param key
     * @param data
     * @return
     */
    public static String aesDecrypt(byte[] key,String data){
        AES    aes = new AES(Mode.ECB, Padding.ZeroPadding, key);
        String msg = aes.decryptStr(data);
        return msg;
    }

    /**
     * AES加密
     * @param key
     * @param data
     * @return
     */
    public static String aesEncrypt(byte[] key,String data){
        AES    aes = new AES(Mode.ECB, Padding.ZeroPadding, key);
        String respMsg = aes.encryptBase64(data);
        return respMsg;
    }

    /**
     * RSA 私钥解密
     */
    public static byte[] rsaDecrypt(RSA rsa,String key){
        byte[] respKey = rsa.decrypt(key, KeyType.PrivateKey);
        return respKey;
    }

    /**
     * RSA 公钥加密
     */
    public static String rsaEncryptBase(RSA rsa,String aesKey){
        String key =  rsa.encryptBase64 (aesKey, KeyType.PublicKey);
        return key;
    }

    public static ColdApiBody decrypt (ColdApiBody request, RSA rsa) {
        boolean flag = false;
        try{
            /**
             * RSA 私钥解密 AES密钥
             */
            byte[] respKey = rsaDecrypt(rsa,request.getKey ());
            log.info ("AES请求密钥:" + new String(respKey));
            /**
             * AES 解密报文
             */
            String msg = request.getData ();
            String respMsg = aesDecrypt(respKey,request.getData ());
            int length =  respMsg.length ();
            if(length <= 4096){
                log.info ("解密报文:" + respMsg);
            }else{
                log.info ("解密报文截取4096位:" + respMsg.substring (0,4096));
            }
            /**
             * RSA 公钥验签
             */
            flag = verify(request,msg,new String(respKey),request.getSign (),rsa.getPublicKey ());
            request.setData (respMsg);
        }catch (Exception e){
            throw new RuntimeException("报文解密异常");
        }
        if(!flag){
            throw new RuntimeException("验签失败");
        }else{
            log.info ("验签通过");
        }
        return request;
    }

    /**
     * 使用RSA解密AES密钥 【 私钥解密 】
     * 加密AES解密报文
     * 使用RSA 【 公钥验签 】
     * @param request
     * @param privateKey
     * @param publicKey
     * @return
     */
    public static ColdApiBody decrypt (ColdApiBody request, String privateKey,String publicKey) {
        RSA rsa = new RSA(privateKey,publicKey);
        return decrypt(request,rsa);
    }

    public static ColdApiBody encrypt(ColdApiBody request, RSA rsa){
        try{
            String oldMsg = request.getData ();
            byte[] aesKey = getKey ().getBytes(StandardCharsets.UTF_8);
            log.info ("随机AES密钥:" + new String(aesKey));
            /**
             * RSA 公钥加密 AES密钥
             */
            String key = rsaEncryptBase (rsa,new String(aesKey));
            log.info ("随机AES加密密钥:" + key);
            /**
             * AES 加密报文
             */
            String      msg     = aesEncrypt (aesKey,oldMsg);
            request.setKey (key);
            request.setData (msg);
            /**
             * RSA 私钥签名
             */
            request.setSign (sign (request,msg,new String(aesKey),rsa.getPrivateKey ()));
            String message = JSONUtil.toJsonStr(request);
            int length =  message.length ();
            if(length <= 4096){
                log.info ("请求参数:" + message);
            }else{
                log.info ("请求参数截取4096位:" + message.substring (0,4096));
            }
            return request;
        }catch (Exception e){
            throw new RuntimeException("报文加密异常");
        }
    }
    /**
     * 使用RSA加密AES密钥 【 公钥加密 】
     * 加密AES加密报文
     * 使用RSA【 私钥签名 】
     * @param request
     * @param privateKey
     * @param publicKey
     * @return
     */
    public static ColdApiBody encrypt(ColdApiBody request, String privateKey,String publicKey){
        RSA rsa = new RSA(privateKey,publicKey);
        return encrypt(request,rsa);
    }

    /**
     * 签名
     * @param key
     * @param request
     * @param privateKey
     * @return
     */
    public static String sign(ColdApiBody request,String msg ,String key, PrivateKey privateKey){
        Sign          sign    = new Sign(SignAlgorithm.SHA1withRSA, privateKey, null);
        BASE64Encoder encoder = new BASE64Encoder();
        String paramsJson = signData(request,msg,key);
        log.info ("签名内容:" + paramsJson);
        return encoder.encode (sign.sign(paramsJson.getBytes(StandardCharsets.UTF_8)));
    }

    private static String signData (ColdApiBody request, String msg,String key) {
        TreeMap<String, Object> map  = new TreeMap<> ();
        map.put ("key", key);
        map.put ("logId", request.getLogId ());
        map.put ("timestamp", request.getTimestamp ());
        map.put ("orgNo", request.getOrgNo ());
        map.put ("channelId", request.getChannelId ());
        map.put ("msg", msg);
        return JSONUtil.toJsonStr(map);
    }

    /**
     * 验签
     * @param key
     * @param request
     * @param signValue
     * @param publicKey
     * @return
     */
    @SneakyThrows
    public static boolean verify(ColdApiBody request,String msg,String key, String signValue, PublicKey publicKey){
        String paramsJson = signData(request,msg,key);
        log.info ("验证签名内容:" + paramsJson);
        Sign          sign    = new Sign(SignAlgorithm.SHA1withRSA, null, publicKey);
        BASE64Decoder decoder = new BASE64Decoder();
        return sign.verify(paramsJson.getBytes(StandardCharsets.UTF_8), decoder.decodeBuffer (signValue));
    }

    public static String getKey(){
        StringBuilder str    = new StringBuilder();
        Random        random =new Random();
        for(int i=0;i<8;i++){
            str.append(random.nextInt(10));
        }
        Random rand1 = new Random();
        int choice = rand1.nextInt(2) % 2 == 0 ? 65 : 97;
        for (int i = 0; i < 8; i++) {
            str.append((char)(choice + rand1.nextInt(26)));
        }
        return str.toString().toUpperCase ();
    }
}
