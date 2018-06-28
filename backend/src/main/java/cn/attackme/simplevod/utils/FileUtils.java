package cn.attackme.simplevod.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 文件操作工具类
 */
public class FileUtils {
    /**
     * 读取文件
     * @param src
     * @param target
     * @throws IOException
     */
   public static void read(String src, OutputStream target) throws IOException {
       Files.copy(Paths.get(src), target);
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
}
