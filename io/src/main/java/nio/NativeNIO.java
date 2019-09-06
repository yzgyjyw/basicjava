package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NativeNIO {

    public static void main(String[] args) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(16);

        Selector selector = Selector.open();

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket serverSocket = serverChannel.socket();
        serverSocket.bind(new InetSocketAddress("localhost", 8888));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("begin to lisetn 8888 ...");


        while (true) {
            int num = selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = ssc.accept();
                    socketChannel.configureBlocking(false);
                    SelectionKey newKey = socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("accepted");
                    iterator.remove();
                } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                    System.out.println("read");
                    SocketChannel channel = (SocketChannel) key.channel();

                    buffer.clear();
                    String result = "";
                    int readed = 0;
                    while ((readed = channel.read(buffer)) > 0) {
                        buffer.flip();
                        byte[] bytes = new byte[readed];
                        buffer.get(bytes);
                        result += new String(bytes);
                        buffer.clear();
                    }

                    System.out.println("read from " + channel + " :" + result);

                    iterator.remove();
                }
            }
        }
    }

}

class MyServer {
    public void start() throws IOException {


    }

    public void readWrite() {

    }
}
