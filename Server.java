import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;


public class Server {
    private static final int port = 1234;
    private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) {
        try {
            MySelector mySelector = new MySelector(port);
            new Thread(mySelector).start();
            System.out.println(dateFormat.format(new Date()) + " Server listening on port " + port + ".");
        } catch (IOException e) {
            System.err.println(dateFormat.format(new Date()) + " Couldn't open the server, consider trying another port.");
        }
    }

    static class MySelector implements Runnable {

        final Selector selector;
        final ServerSocketChannel serverSocketChannel;

        /*
        Notice that the channel must be in non-blocking mode to be used with a Selector
        The serverSocketChannel will listen to incoming connection petitions from clients' socket
        */
        public MySelector(int port) throws IOException {

            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            SelectionKey mySelectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            mySelectionKey.attach(new Acceptor());
        }

        //We use the selector in order to run a ServerProtocol thread for each successfully connected client
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    selector.select();
                    Set<SelectionKey> selected = selector.selectedKeys();
                    Iterator<SelectionKey> it = selected.iterator();
                    while (it.hasNext()) {
                        dispatch(it.next());
                    }
                    selected.clear();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        //Runs a thread (ServerProtocol) to manage a connected client
        void dispatch(SelectionKey k) {
            Runnable r = (Runnable) (k.attachment());
            if (r != null) {
                r.run();
            }
        }

        //This thread accepts incoming connections
        class Acceptor implements Runnable {
            public void run() {
                try {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    if (socketChannel != null) {
                        new ServerProtocol(selector, socketChannel);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
