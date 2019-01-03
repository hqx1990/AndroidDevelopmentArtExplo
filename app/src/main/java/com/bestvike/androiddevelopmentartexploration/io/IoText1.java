package com.bestvike.androiddevelopmentartexploration.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class IoText1 {
    private static String path = "C:/Users/ThinkPad/Desktop/ff";

    public static void main(String [] args){
        system("这是个IO流练习文本");
        //在桌面创建一个文件夹ff
//        createFolder();
//
//        createFile();//创建一个文件
//
//        writeFile();//写入内容到文件中
//
//        readFile();//读出文件内容
        system(string2MD5("distId=1234567&phone=12333212&thirdDbid=1234567caissa"));
    }

    /**
     * 创建一个文件夹
     */
    private static void createFolder(){
        File file = new File(path);
        //判断这个文件夹是否存在
        if(file.isDirectory()){
            //该文件存夹在
            system("桌面已经存在该文件夹！");
            //将文件下所有文件组成数组
            File fileList[] = file.listFiles();
            system("ff文件夹下有"+fileList.length+"个文件");
            //取出所有文件的名称
            for (int i = 0;i<fileList.length;i++){
                system("文件名："+fileList[i].getName());
            }
        }else{
            file.mkdirs();//创建文件
            system("该文件夹不存在！文件夹创建完成");
        }

    }

    /**
     * 在文件夹下创建一个文件
     */
    private static void createFile(){
        //创建新的文件
        File file2 = new File(path+"/aa.txt");
        if(!file2.exists()){
            try {
                file2.createNewFile();//创建文件
                system("该文件不存在，创建文件完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            system("已存该文件aa.txt");
        }
    }

    /**
     * 写入内容到文件中
     */
    private static void writeFile(){
        File file = new File(path+"/aa.txt");
        if(file.exists()){
            //该文件存在
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);
                String str1 = "写入内容到文件中 \r";
                String str2 = "练习";
                fileOutputStream.write(str1.getBytes());//写内容到文件
                fileOutputStream.write(str2.getBytes());
                system("内容写入完成");
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    fileOutputStream.close();//关闭流
                    system("关闭写入流");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            system("文件aa.txt不存在");
        }
    }

    /**
     * 读出文件内容
     */
    private static void readFile(){
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(path+"/aa.txt"));
            fileOutputStream = new FileOutputStream(new File(path+"/aa.txt"));
            byte bytes[] = new byte[1024];
            int n = 0;
            while ((n = fileInputStream.read(bytes))!= -1){
                fileOutputStream.write(bytes,0,n);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void system(String str){
        System.out.println(str);
    }

    public static String string2MD5(String inStr)
    {
        MessageDigest md5 = null;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++)
        {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }
}
