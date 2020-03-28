package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 在nio中,所有的写入写出都是针对buffer(缓冲区)的,与原有api中的stream进行了区分
 * 缓冲区通常是一个数组，一般是字节数组，但也可以是其它类型的数组，当然buffer对这些数组进行了数据结构的封装
 */
public class BufferDemo {

    public static void main(String[] args) throws IOException {
        slice();
//        readOnlyBuffer();
//        mapperIO();
//        directBuffer();
//        typeByteBuffer();
    }

    //缓冲区分片操作
    public static void slice() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        System.out.println(buffer.position());  //10
        System.out.println(buffer.limit());     //10

        buffer.position(3);
        buffer.limit(7);
        //从当前的pos位置到limit位置切片,不过该切片返回的sliceBuffer与原buffer共享同一个底层的字节数组
        //[3,7)
        ByteBuffer sliceBuffer = buffer.slice();//java.nio.HeapByteBuffer[pos=0 lim=4 cap=4]

        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            byte b = sliceBuffer.get(i);
            sliceBuffer.put(i, (byte) (b * 11));
        }

        System.out.println(sliceBuffer.position());  //0
        System.out.println(sliceBuffer.limit());     //4

        //flip操作：将position赋值给limit，同时position置为0
//        buffer.flip();
        buffer.position(0);
        buffer.limit(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.get());
        }
    }

    public static void readOnlyBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        //将原缓冲区转换为只读缓冲区,两个缓冲区共用一个底层数组
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        //原缓冲区依旧可以写入
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
    }

    public static void mapperIO() throws IOException {
        FileInputStream inputStream = new FileInputStream("abc.txt");
        FileChannel iChannel = inputStream.getChannel();
        //将文件的前20个字节映射到内存中,不过这边是输入流,因此MapMode必须是READ_ONLY,如果需要Mode为READ_WRITE,可以借助
        // RandomAccessFile raf = new RandomAccessFile( "usemappedfile.txt", "rw" );
        //MappedByteBuffer 是ByteBuffer的子类,可以向针对ByteBuff一样对其进行操作
        //操作之后position=0,,limit=20
        MappedByteBuffer mbb = iChannel.map(FileChannel.MapMode.READ_ONLY, 0, 20);
        //不需要再flip
//        mbb.flip();
        byte[] bytes = new byte[20];
        mbb.get(bytes, 0, 20);
        System.out.println(new String(bytes));
    }

    //直接缓冲区是以一种特殊方式分配其内存的缓冲区，其具体实现与JVM实现者有关
    //给定一个直接字节缓冲区，Java 虚拟机将尽最大努力直接对它执行本机 I/O 操作。也就是说，它会在每一次调用底层操作系统的本机 I/O 操作之前(或之后)
    //尝试避免将缓冲区的内容拷贝到一个中间缓冲区中(或者从一个中间缓冲区中拷贝数据)。
    public static void directBuffer() throws IOException {
        FileInputStream inputStream = new FileInputStream("abc.txt");
        FileChannel iChannel = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream("bcd.txt");
        FileChannel oChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(10);

        while(iChannel.read(buffer)!=-1){
            buffer.flip();
            oChannel.write(buffer);
            buffer.clear();
        }

        inputStream.close();
        outputStream.close();
    }

    public static void typeByteBuffer(){
        ByteBuffer buffer = ByteBuffer.allocate(20);

        buffer.putInt(10);
        buffer.putLong(999999999999999999L);
        buffer.putDouble(Math.PI);

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());

    }

}
