package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * channel是nio中除了buffer以外的又一个重要的概念
 * 通过通道可以进行数据的读写，但是永远不会直接写入数据到通道或者直接从通道中读取数据，都必须进过buffer
 *
 * 从通道中读取数据：先创建一个缓冲区，让通道将数据读到缓冲区中
 * 写数据到通道：创建一个缓冲区，然后用数据填充它，通道用这些数据进行写操作
 * https://www.ibm.com/developerworks/cn/education/java/j-nio/j-nio.html
 */
public class ChannelDemo {

    //从文件中使用通道读取数据(注意不是直接从通道读取，而是从buffer中读取)
    public static void main(String[] args) throws IOException {
        copyFile("/home/mi/IdeaProjects/basicjava/abc.txt","def.txt");
    }

    public static void init() throws IOException {

        //1. 创建一个FileInputStream
        FileInputStream inputStream = new FileInputStream("abc.txt");
        //2. 根据FileInputStream,创建Channel
        FileChannel channel = inputStream.getChannel();
        //3. 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //4. 将数据从通道读到缓冲区
        channel.read(buffer);
        channel.close();
        inputStream.close();
    }

    public static void copyFile(String srcPath,String destPath) throws IOException {
        FileInputStream inputStream = new FileInputStream(srcPath);
        FileChannel iChannel = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream(destPath);
        FileChannel oChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(10);

        while(iChannel.read(buffer)!=-1){
            buffer.flip();
            oChannel.write(buffer);
            buffer.clear();
        }

        inputStream.close();
        outputStream.close();

    }
}
