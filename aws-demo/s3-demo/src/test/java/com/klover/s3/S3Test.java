package com.klover.s3;

import org.junit.jupiter.api.Test;

import java.io.*;

class S3Test {

    @Test
    void putFile() {
        String fileUrl = S3.putFile("4.png", new File("C:\\Users\\klover\\Desktop\\file\\4.png"));
    }

    @Test
    void putFile2() throws FileNotFoundException {
        String fileUrl = S3.putFile("5.png", new FileInputStream("C:\\Users\\klover\\Desktop\\file\\4.png"));
    }

    @Test
    void getObject() throws IOException {
        InputStream inputStream = S3.getObject("4.png");
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\klover\\Desktop\\file\\8.png");

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        // 关闭流
        inputStream.close();
        outputStream.close();

        System.out.println("文件写入完成。");
    }
}