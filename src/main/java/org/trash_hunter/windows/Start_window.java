package org.trash_hunter.windows;

import org.trash_hunter.Diver;
import org.trash_hunter.DiverDAO;
import org.trash_hunter.util.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Start_window extends JFrame implements WindowListener {
    private JPanel startPanel;
    private JTextField inputName;
    private JLabel pseudoLabel;
    private JLabel couleurLabel;
    private JButton validateButton;
    private JComboBox comboBox1;
    private boolean isclosed = false;
    private static String pseudo;
    private static String color;
    public static DiverDAO diverDAO;



    public Start_window(JFrame parent) throws SQLException {
        setContentPane(startPanel);
        setTitle("Trash Hunter");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo (parent);
        setSize(300,400);                     //taille de la fenêtre
        setLocationRelativeTo(null);
        setVisible(false);
        diverDAO = new DiverDAO(DatabaseConnection.getConnection());
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> usernames = diverDAO.findAllPseudo();
                if (inputName.getText().isBlank()){
                    pseudo = "Bob";
                }
                if (usernames.contains(inputName.getText())||usernames.contains(pseudo)) {
                    JOptionPane.showMessageDialog(null,
                            "Ce pseudo est déjà utilisé veuillez changer!",
                            "Attention",
                            JOptionPane.INFORMATION_MESSAGE);
                }else {
                    pseudo = inputName.getText();
                    color = (String) comboBox1.getSelectedItem();
                    JComponent comp = (JComponent) e.getSource();
                    Window win = SwingUtilities.getWindowAncestor(comp);
                    win.dispose();
                }
                System.out.println(pseudo);
                System.out.println(color);
            }
        });
        setVisible(true);
    }


    public static String getPseudo() {
        return pseudo;
    }
    public static String getColor(){
        return color;
    }

    public boolean Isclosed() {
        return isclosed;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        this.isclosed=true;
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}
