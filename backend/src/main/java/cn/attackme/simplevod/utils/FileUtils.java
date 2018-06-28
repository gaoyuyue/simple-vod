package cn.attackme.simplevod.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 文件操作工具类
 */
public class FileUtils {
    /**
     * 判断文件是否存在
     * @param path
     * @return
     */
    public static boolean isExist(String path) {
        return Files.exists(Paths.get(path));
    }

    /**
     * 获取指定文件的长度
     * @param path
     * @return
     */
    public static long getLength(String path) {
        return new File(path).length();
    }

    /**
     * 读取文件
     * @param src
     * @param target
     * @throws IOException
     */
    public static void read(String src, OutputStream target) throws IOException {
        Files.copy(Paths.get(src), target);
        target.flush();
    }

    /**
     * 从文件指定位置读取
     * @param src
     * @param target
     * @param start
     */
    public static void readWithSeek(String src, OutputStream target, long start) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(src, "r");
        randomAccessFile.seek(start);
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = randomAccessFile.read(buf))) {
            target.write(buf, 0, len);
        }
        target.flush();
        randomAccessFile.close();
    }

    /**
     * 指定随机文件读取的开始位置,和读取length
     * @param src
     * @param target
     * @param start
     * @param length
     * @throws IOException
     */
    public static void readWithSeek(String src, OutputStream target, long start, long length) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(src, "r");
        randomAccessFile.seek(start);
        byte[] buf = new byte[1024];
        int len;
        while (length > 1024) {
            len = randomAccessFile.read(buf);
            target.write(buf, 0, len);
            length -= len;
        }
        randomAccessFile.read(buf, 0, (int) length);
        target.write(buf, 0, (int) length);
        target.flush();
        randomAccessFile.close();
    }
}
