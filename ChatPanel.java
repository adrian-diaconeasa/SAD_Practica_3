
import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {
    private final JTextArea chatText;
    private final JButton enviar;
    private final JButton desconectar;
    private final JTextField messageField;
    private final DefaultListModel usersListModel;
    private final JList<String> users;
    private final GridBagConstraints c;

    public ChatPanel(String connectionString) {
        super(new GridBagLayout());
        chatText = new JTextArea(connectionString);
        enviar = new JButton("Enviar");
        desconectar = new JButton("Desconectar");
        messageField = new JTextField();
        c = new GridBagConstraints();
        usersListModel = new DefaultListModel<>();
        users = new JList<>(usersListModel);

        setupGUI();
    }

    private void setupGUI() {
        setBackground(Color.DARK_GRAY);

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(40, 40, 10, 10);
        chatText.setBackground(Color.WHITE);
        chatText.setEditable(false);
        chatText.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatText);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, c);

        c.gridy = 1;
        c.gridx = 1;
        c.gridheight = 1;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.NORTHEAST;
        c.insets = new Insets(0, 10, 10, 40);
        c.ipadx = 50;
        users.setLayoutOrientation(JList.VERTICAL);
        users.setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(users), c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(10, 40, 40, 10);
        c.ipady = 30;
        c.ipadx = 0;
        messageField.setBackground(Color.WHITE);
        add(messageField, c);

        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0;
        c.weighty = 0;
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(10, 10, 40, 40);
        c.ipadx = 0;
        add(enviar, c);
       
        
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTHEAST;
        c.insets = new Insets(15, 15, 15, 15);
        add(desconectar, c);
    }

    public JButton getSendButton(){ return enviar; }
    public JButton getDisconnectButton(){ return desconectar;}    
    public JTextArea getChatText(){ return chatText; }
    public JTextField getMessageField(){ return messageField; }
    public DefaultListModel getUsersList(){ return usersListModel; }
}
