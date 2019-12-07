package cn.com.zxh.chat.utils;

import org.springframework.stereotype.Component;

import java.io.*;

//文件工具类  用来将数据写入某个文件中 或从某个文件中读取数据
@Component
public class FileUtils {

    /**
     * 将数据 写入 指定文档中
     *
     * @param str    要写入的数据
     * @param file   要写入的文档名
     * @param append 0：为覆盖文本内容，1：为续写
     */
    public void write(String str, String file, int append) {

        //创建写入路径
        File writeName = null;
        //创建字符缓存流-写
        BufferedWriter writer = null;
        try {
            //指定写入路径
            writeName = new File("src\\main\\resources\\data\\" + file + ".txt");
            writeName.createNewFile();
            if (append == 0) {
                //覆盖
                writer = new BufferedWriter(new FileWriter(writeName));
            } else {
                //追加
                writer = new BufferedWriter(new FileWriter(writeName, true));
            }
            //写入
            writer.write(str);
            // 把缓存区内容压入文件
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭字符流
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 从指定文档中读取数据
     *
     * @param file 要读取的文档名
     * @return
     */
    public String read(String file) {
        //创建读取路径
        File readName = null;
        //创建字符缓冲流-读
        BufferedReader reader = null;
        //创建字符串
        String str = "";
        try {
            //指定读取路径
            readName = new File("src\\main\\resources\\data\\" + file + ".txt");
            readName.createNewFile();
            reader = new BufferedReader(new FileReader(readName));
            String line = "";
            line = reader.readLine();
            //读取文件所有数据
            while (line != null) {
                str += line + ",";
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭字符流
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
