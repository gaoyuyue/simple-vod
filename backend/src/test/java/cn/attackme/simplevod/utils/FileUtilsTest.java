package cn.attackme.simplevod.utils;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileUtilsTest {
    private static String srcPath;
    private static Path directory;

    @BeforeClass
    public static void getFile() throws IOException {
        srcPath = Thread.currentThread().getContextClassLoader().getResource("cn/attackme/simplevod/utils/FileUtils.class").getPath();
        directory = Files.createTempDirectory(null);
    }

    /**
     * 读文件成功测试
     * @throws IOException
     */
    @Test
    public void testReadFileSuccess() throws IOException {
        File targetFile = new File(directory.toString()+"/"+UUID.randomUUID().toString());
        FileOutputStream fos = new FileOutputStream(targetFile);
        FileUtils.read(srcPath, fos);
        File srcFile = new File(srcPath);
        Assert.assertEquals(srcFile.length(), targetFile.length());
    }

    /**
     * 读不存在的文件抛出异常测试
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public void testReadNotExistFileThrowException() throws IOException {
        File targetFile = new File(directory.toString()+"/"+UUID.randomUUID().toString());
        FileOutputStream fos = new FileOutputStream(targetFile);
        FileUtils.read(UUID.randomUUID().toString(), fos);
    }

    /**
     * 随机读取文件成功测试
     * @throws IOException
     */
    @Test
    public void testReadWithSeekFileSuccess() throws IOException {
        File srcFile = new File(srcPath);
        File targetFile = new File(directory.toString()+"/"+UUID.randomUUID().toString());
        FileOutputStream fos = new FileOutputStream(targetFile);
        FileUtils.readWithSeek(srcPath, fos, srcFile.length()>>1);
        Assert.assertEquals(srcFile.length()-(srcFile.length()>>1), targetFile.length());
    }

    /**
     * 随即读取不存在的文件抛出异常测试
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public void testReadWithSeekNotExistFileThrowException() throws IOException {
        File targetFile = new File(directory.toString()+"/"+UUID.randomUUID().toString());
        FileOutputStream fos = new FileOutputStream(targetFile);
        FileUtils.readWithSeek(UUID.randomUUID().toString(), fos, 0);
    }

    /**
     * 随机读文件设置非法seek(即seek为负数)，抛出异常测试
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public void testReadWithSeekNegativeSeekOffsetThrowException() throws IOException {
        File targetFile = new File(directory.toString()+"/"+UUID.randomUUID().toString());
        FileOutputStream fos = new FileOutputStream(targetFile);
        FileUtils.readWithSeek(srcPath, fos, -1);
    }


    /**
     * 随机读取文件设置超过文件length的seek，自动回环
     * @throws IOException
     */
    @Test
    public void testReadWithSeekOutLengthSeekRound() throws IOException {
        File srcFile = new File(srcPath);
        File targetFile = new File(directory.toString() + "/" + UUID.randomUUID().toString());
        FileOutputStream fos = new FileOutputStream(targetFile);
        FileUtils.readWithSeek(srcPath, fos, targetFile.length() + 2);
        Assert.assertEquals(srcFile.length() - 2, targetFile.length());
    }
}
