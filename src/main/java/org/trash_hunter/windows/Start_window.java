package org.trash_hunter.windows;

import org.trash_hunter.DiverDAO;
import org.trash_hunter.util.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class Start_window extends JFrame {
    private JPanel StartPanel;
    private JTextField inputName;
    private JLabel pseudoLabel;
    private JLabel couleurLabel;
    private JButton validateButton;
    private JComboBox comboBox1;
    private Connection connection;
    public static String pseudo;

    public Start_window() throws SQLException {

        setContentPane(StartPanel);
        setTitle("Trash Hunter");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(300,400);
        setVisible(true);
        initializeDatabaseConnection();
        DiverDAO diverDAO = new DiverDAO(connection);
        this.pseudo=inputName.getText();
        validateButton.addActionListener(new ActionListener() {
            private boolean isclosed;
            @Override
            public void actionPerformed(ActionEvent e) {
                for (String pseudo : diverDAO.findAllPseudo()){
                    if (inputName.getText().equals(pseudo)) {
                        JOptionPane.showMessageDialog(null,
                                "Ce nom d'utilisateur est déjà utilisé !",
                                "Attention",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        setLocationRelativeTo(null);
    }


    private void initializeDatabaseConnection() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }



}
