package org.trash_hunter.windows;

import org.trash_hunter.DiverDAO;
import org.trash_hunter.GamePanel;
import org.trash_hunter.util.DatabaseConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class Start_window extends JFrame implements WindowListener {
    private JPanel startPanel;
    private JTextField inputName;
    private JLabel pseudoLabel;
    private JLabel couleurLabel;
    private JButton validateButton;
    private JComboBox comboBox1;
    private JButton exitButton;
    private JLabel imageLogo;
    private JLabel Title;
    private boolean isclosed = false;
    private static String pseudo;
    private static String color;
    public static DiverDAO diverDAO;



    public Start_window() throws SQLException {
        setContentPane(startPanel);
        setTitle("Trash Hunter");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(350,400);                     //taille de la fenêtre
        setLocationRelativeTo(null);
        setVisible(true);
        diverDAO = new DiverDAO(DatabaseConnection.getConnection());
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> usernames = diverDAO.findAllPseudo();
                if (inputName.getText().isBlank()){
                    JOptionPane.showMessageDialog(null,
                            "Rentrer un pseudo!",
                            "Attention",
                            JOptionPane.YES_NO_OPTION);
                }
                if (usernames.contains(inputName.getText())) {
                    JOptionPane.showMessageDialog(null,
                            "Ce pseudo est déjà utilisé veuillez changer!",
                            "Attention",
                            JOptionPane.YES_NO_OPTION);
                }
                if(!inputName.getText().isBlank() && !usernames.contains(inputName.getText())){
                    pseudo = inputName.getText();
                    color = (String) comboBox1.getSelectedItem();

                    //Créer et afficher le gamepanel avec pour arguments le pseudo et la couleur
                    try{
                        GamePanel panel = new GamePanel(pseudo,color);
                        panel.setVisible(true);
                    }catch(SQLException ex){
                        ex.printStackTrace();
                    }
                    JComponent comp = (JComponent) e.getSource();
                    Window win = SwingUtilities.getWindowAncestor(comp);
                    win.dispose();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null,"Certain de vouloir quitter ?", "Attention",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
                if(result == JOptionPane.NO_OPTION){
                }
            }
        });
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

    private void createUIComponents() {
        try {
            InputStream imageStream = getClass().getClassLoader().getResourceAsStream("plongeur.png");
            if (imageStream == null) {
                throw new IOException("Image non trouvée : plongeur.png");
            }

            // Chargez l'image du plongeur
            Icon logo = new ImageIcon(ImageIO.read(imageStream));
            imageLogo = new JLabel(logo);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
    }
}
