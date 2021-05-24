import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Client {
    private static final String hostName = "localhost";
    private static final int port = 1234;

    private static String userName;
    private static ChatFrame chatFrame;
    private static MySocket mySocket;

    public static void main(String[] args) {
        chatFrame = new ChatFrame();
        setUpUserName();
    }

    public static void setUpUserName() {
        chatFrame.getLoginPanel().getNicknameField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chatFrame.getLoginPanel().getJoinButton().doClick();
            }
        });

        chatFrame.getLoginPanel().getJoinButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                userName = chatFrame.getLoginPanel().getNicknameField().getText();
                if (userName.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Couldn't join the chat, empty username",
                            "Empty username",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    connectToServer();
                }

            }
        });
    }

    public static void setUpChat(String connection) {
        chatFrame.setupChatPanel(userName);
        chatFrame.getChatPanel().getChatText().append(connection);

        // Send Button clicked
        chatFrame.getChatPanel().getSendButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String message = chatFrame.getChatPanel().getMessageField().getText();
                if (message.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "You can't send an empty message!",
                            "Empty Message",
                            JOptionPane.ERROR_MESSAGE);

                } else {
                    sendMessage(message);
                    chatFrame.getChatPanel().getMessageField().setText("");
                }
            }
        });

        // Disconnect button clicked
        chatFrame.getChatPanel().getDisconnectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                disconnect();
            }
        });
    }

    public static void sendMessage(String message) {
        mySocket.printLine(message);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        writeInChat(dateFormat.format(new Date()) + " ["+ userName + "]: " + message);
    }

    public static void connectToServer() {
        mySocket = new MySocket(userName, hostName, port);

        //This thread listens and "process" the information coming from the ServerProtocol
        Thread outputThread = new Thread(() -> {
            String line;
            while ((line = mySocket.readLine()) != null) {
                if (line.contains("[HELLO CLIENT]")) {
                    String str = line.substring(line.indexOf("]") + 2);
                    setUpChat(str);

                    line = mySocket.readLine();
                    str = line.substring(line.indexOf("]") + 1);
                    while (str.contains(";")) {
                        String user;
                        if (str.indexOf(";", 1) != -1) {
                            user = str.substring(1, str.indexOf(";", 1));
                            str = str.substring(str.indexOf(";", 1));
                        } else {
                            user = str.substring(1);
                            str = "";
                        }
                        chatFrame.getChatPanel().getUsersList().addElement(user);
                        chatFrame.revalidate();
                        chatFrame.repaint();
                    }

                } else if (line.contains("[CLIENT CONNECTED]")) {
                    String str = line.substring(line.indexOf("]") + 2);
                    chatFrame.getChatPanel().getUsersList().addElement(str);

                } else if (line.contains("[CLIENT DISCONNECTED]")) {
                    String str = line.substring(line.indexOf("]") + 2);
                    chatFrame.getChatPanel().getUsersList().removeElement(str);

                } else {
                    writeInChat(line);
                }
            }
        });
        outputThread.start();
    }

    public static void writeInChat(String text) {
        chatFrame.getChatPanel().getChatText().append("\n" + text);
    }

    public static void disconnect() {
        mySocket.close();
        chatFrame.dispose();
        System.exit(0);
    }
}
