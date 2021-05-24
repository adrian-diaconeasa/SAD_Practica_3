import java.io.*;
import java.net.Socket;

public class MySocket extends Socket {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public MySocket(String userName, String hostName, int port) {
        try {
            this.socket = new Socket(hostName, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            printLine(userName);

        } catch (IOException e) {
            System.err.println("No ha sido posible crear el socket");
            e.printStackTrace();
        }
    }


    public String readLine() {
        String s = null;
        try {
            s = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public void printLine(String s) {
        output.println(s);
        output.flush();
    }

    @Override
    public void close() {
        try {
            printLine("EXIT");
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("No ha sido posible cerrar el socket");
        }
    }
}
