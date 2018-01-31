package org.jozif;

import com.sina.cloudstorage.auth.AWSCredentials;
import com.sina.cloudstorage.auth.BasicAWSCredentials;
import com.sina.cloudstorage.services.scs.SCS;
import com.sina.cloudstorage.services.scs.SCSClient;
import com.sina.cloudstorage.services.scs.model.PutObjectResult;

import java.io.File;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        new App().run();
    }

    public void run() {
        //加密后的accessKey和secretKey，可以登录 http://open.sinastorage.com 查看
        String accessKeyEncoded = "038fae0ceb8d9ca1664b128fbac7d829d7e1b2f48da7a48c029f4d66ea2d81c9";
        String secretKeyEncoded = "16e44ec37ee04a43e96e40c73a6fc9eadc3983715edeb847c2abdc43ba814b4a4680c270a77edcc7";

        //从控制台输入私钥，用来解密加密后的accessKey和secretKey
        Scanner sc=new Scanner(System.in);
        System.out.print("input key：");
        String key=sc.next();

        //解密
        String accessKey = XxteaUtils.decrypt(accessKeyEncoded, key);
        String secretKey = XxteaUtils.decrypt(secretKeyEncoded, key);

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        SCS conn = new SCSClient(credentials);

        //上传object
        PutObjectResult putObjectResult = conn.putObject("ittac-test-bucket",
                "uploadPathAndName.txt", new File("test.txt"));

        System.out.println(putObjectResult);
    }
}
