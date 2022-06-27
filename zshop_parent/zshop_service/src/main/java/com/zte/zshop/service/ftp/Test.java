package com.zte.zshop.service.ftp;

import com.zte.zshop.utils.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author:helloboy
 * Date:2022-05-06 15:19
 * Description:<描述>
 * 使用java代码完成将图片(二进制文件)上传到ftp指定目录
 */
public class Test {

    public static void main(String[] args) throws IOException {

        testFTP();

    }

    private static void testFTP() throws IOException {

        //创建客户端对象
        FTPClient ftp = new FTPClient();
        InputStream local=null;
        try {
            //连接ftp服务器
            ftp.connect("localhost",21);
            //登录
            ftp.login("mike","123");
            //设置上传路径,主目录下的路径
            String path="/2/3";
            //检查上传路径是否存在，如果不存在，可以创建
            boolean flag = ftp.changeWorkingDirectory(path);
            //如果不存在false,创建该上传路径
            if(!flag){
               boolean b= ftp.makeDirectory(path);
               System.out.println("b---->"+b);
            }
            //指定上传路径
            ftp.changeWorkingDirectory(path);

            //指定文件类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //读取本地文件
            File file = new File("d:\\a.jpeg");
            //设置本地输入流
            local = new FileInputStream(file);

            String fileName = StringUtils.renameFileName(file.getName());
            //将流写入对应的目录，完成上传动作
            boolean c = ftp.storeFile(fileName, local);
            System.out.println("c--->"+c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                //关闭文件流
                local.close();
                //退出
                ftp.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
