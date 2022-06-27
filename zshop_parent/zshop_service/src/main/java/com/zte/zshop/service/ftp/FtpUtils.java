package com.zte.zshop.service.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;


public class FtpUtils {


    public static String pictureUploadByConfig(FtpConfig ftpConfig,String picNewName,String picSavePath,
                                               InputStream inputStream) throws IOException{

        String picHttpPath = null;


        boolean flag = uploadFile(ftpConfig.getFTP_ADDRESS(), ftpConfig.getFTP_PORT(), ftpConfig.getFTP_USERNAME(),
                ftpConfig.getFTP_PASSWORD(), ftpConfig.getFTP_BASEPATH(), picSavePath, picNewName, inputStream);

        if(!flag){
            return picHttpPath;
        }

        //picHttpPath = ftpConfig.getFTP_ADDRESS()+"/images"+picSavePath+"/"+picNewName;
        picHttpPath = ftpConfig.getIMAGE_BASE_URL()+picSavePath+"/"+picNewName;
        System.out.println("==="+picHttpPath);
        return picHttpPath;
    }





    public static boolean uploadFile(String host, String ftpPort, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        int port = Integer.parseInt(ftpPort);
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }

            // 检查上传路径是否存在 如果不存在返回false
            boolean flag = ftp.changeWorkingDirectory(basePath+filePath);
            if (!flag) {
                // 创建上传的路径 该方法只能创建一级目录，在这里如果/home/ftpuser存在则可创建image
                boolean b = ftp.makeDirectory(basePath+filePath);
                System.out.println("aaa-->"+b);
            }
            // 指定上传路径
            ftp.changeWorkingDirectory(basePath+filePath);
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();//这个设置允许被动连接--访问远程ftp时需要
            //上传文件
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    
}
