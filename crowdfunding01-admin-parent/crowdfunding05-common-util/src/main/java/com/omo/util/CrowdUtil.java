package com.omo.util;

import com.omo.constant.CrowdConstant;
import org.omg.SendingContext.RunTime;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author MoYu
 * @create 2021-05-04 16:54
 */
public class CrowdUtil {

    public static boolean judgeRequestType(HttpServletRequest request){

        //获取请求头
        String acceptHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-With");

        //判断请求是普通请求还是Ajax请求
        return (acceptHeader != null && acceptHeader.contains("application/json"))

                ||

                (xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest"));
    }

    /**
     *
     *
     * @param source 传入的明文字符串
     * @return  ：加密后的结果
     */

    public static String md5(String source){

        // 1、判断传入的字符串是否有效
        if(source == null || source.length() ==0){

            // 2、如果无效就抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALID);
        }



        // 4、指定采取那种加密格式
        String algorithm = "md5";
        try {

            // 5、创建MessageDigest对象
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 6、获取明文字符串对应的字节数组
            byte[] input = source.getBytes();

            // 7、执行加密
            byte[] output = messageDigest.digest(input);

            int signum = 1;

            // 8、按照16进制将bigInteger的值转换为字符串
            int radix = 16;

            // 9、由于约定数据库中的密码几乎都是数字形式的，需要转换
            // 10、创建BigInteger对象
            return new BigInteger(signum,output).toString(radix).toUpperCase();

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }

        return null;
    }
}
