

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private JLabel nickLabel;
    private JLabel info, bienvenido;
    private JTextField nickField;
    private JButton entrar;
    private GridBagConstraints c;

    public LoginPanel() {
        super(new GridBagLayout());
        setupGUI();
    }

    public void setupGUI() {

        setBackground(Color.LIGHT_GRAY);

        c = new GridBagConstraints();

        // Introducir 
        info = new JLabel("Introduce tu usuario:");
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.SOUTHWEST;
        c.insets = new Insets(40, 0, 0, 40);
        c.gridx = 2;
        c.gridy = 0;
        add(info,c);

        //nick
        nickLabel = new JLabel("Nombre:");
        nickLabel.setFont(new Font("", Font.PLAIN, 15));
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(0, 40, 0, 0);
        c.gridx = 1;
        c.gridy = 1;
        add(nickLabel,c);

        //Bienvenido
        bienvenido = new JLabel("BIENVENIDO");
        bienvenido.setFont(new Font("", Font.BOLD, 25));
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(40, 0, 0, 40);
        c.gridx = 2;
        c.gridy = 0;
        add(bienvenido,c);
        
        //Nick Field
        nickField = new JTextField();
        c.weightx = 1.0;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5, 0, 0, 40);
        c.gridx = 2;
        c.gridy = 1;
        add(nickField,c);

        //Entrar
        entrar = new JButton("Entrar");
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 2;
        c.gridy = 2;
        c.insets = new Insets(15, 0, 40, 40);
        add(entrar,c);
    }

    public JTextField getNicknameField(){
        return nickField;
    }

    public JButton getJoinButton(){
        return entrar;
    }
}

