

import javax.swing.*;
import java.awt.*;

public class ChatFrame extends JFrame {
    private final CardLayout contentCardLayout;
    private final JPanel contentPanel;
    private ChatPanel chatPanel;
    private LoginPanel loginPanel;

    public ChatFrame() {
        contentCardLayout = new CardLayout();
        contentPanel = new JPanel(contentCardLayout);
        add(contentPanel);

        setupLoginPanel();

        setVisible(true);
        pack();
    }

    public void setupLoginPanel(){
        loginPanel = new LoginPanel();
        contentPanel.add(loginPanel, "1");
        contentCardLayout.show(contentPanel, "1");

        setTitle("Swing Chat");
        contentPanel.validate();
        contentPanel.repaint();
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dim = new Dimension(dim.width/3,dim.height/4);
        setSize(dim);
        setLocationRelativeTo(null);
        contentPanel.setPreferredSize(dim);
        loginPanel.setPreferredSize(dim);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setupChatPanel(String nickname){
        chatPanel = new ChatPanel("Bienvenido "+ nickname +"!\n");
        contentPanel.add(chatPanel, "3");
        contentCardLayout.show(contentPanel, "3");

        setTitle("Chat SAD");
        contentPanel.validate();
        contentPanel.repaint();
        setResizable(true);
        Dimension dim = new Dimension(700,700);
        setSize(dim);
        setLocationRelativeTo(null);
        contentPanel.setPreferredSize(dim);
        chatPanel.setPreferredSize(dim);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public ChatPanel getChatPanel() {
        return chatPanel;
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }
}
