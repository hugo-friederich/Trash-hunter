package org.trash_hunter.windows;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.trash_hunter.GamePanel;
import org.trash_hunter.user.DiverDAO;
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
        try {
            // Essayer de créer la connexion à la base de données
            diverDAO = new DiverDAO(DatabaseConnection.getConnection());
        } catch (SQLException e) {
            // Afficher un message d'erreur si la connexion échoue
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion à la base de données : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            // Optionnel : Fermer l'application si la connexion échoue
            System.exit(1);
        }
        setContentPane(startPanel);
        setTitle("Trash Hunter");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(350, 400);                     //taille de la fenêtre
        setLocationRelativeTo(null);
        setVisible(true);
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> usernames = diverDAO.findAllPseudoFromBestScore();
                List<String> currentDivers = diverDAO.findAllPseudoFromDiver();

                if (inputName.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null,
                            "Rentrer un pseudo!",
                            "Attention",
                            JOptionPane.YES_NO_OPTION);
                }
                if (usernames.contains(inputName.getText()) || currentDivers.contains(inputName.getText())) {
                    JOptionPane.showMessageDialog(null,
                            "Ce pseudo est déjà utilisé veuillez changer!",
                            "Attention",
                            JOptionPane.YES_NO_OPTION);
                }
                if (!inputName.getText().isBlank() && !usernames.contains(inputName.getText())) {
                    pseudo = inputName.getText();
                    color = (String) comboBox1.getSelectedItem();

                    //Créer et afficher le gamepanel avec pour arguments le pseudo et la couleur
                    try {
                        GamePanel panel = new GamePanel(pseudo, color);
                        panel.setVisible(true);
                    } catch (SQLException ex) {
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
                int result = JOptionPane.showConfirmDialog(null, "Certain de vouloir quitter ?", "Attention",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                if (result == JOptionPane.NO_OPTION) {
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
        this.isclosed = true;
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        startPanel = new JPanel();
        startPanel.setLayout(new GridLayoutManager(5, 4, new Insets(0, 0, 0, 0), -1, -1));
        startPanel.setEnabled(true);
        inputName = new JTextField();
        startPanel.add(inputName, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        pseudoLabel = new JLabel();
        pseudoLabel.setText("Pseudo");
        startPanel.add(pseudoLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        couleurLabel = new JLabel();
        couleurLabel.setText("Couleur");
        startPanel.add(couleurLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        validateButton = new JButton();
        validateButton.setText("Enregister");
        startPanel.add(validateButton, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        comboBox1.setEditable(false);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Red");
        defaultComboBoxModel1.addElement("Blue");
        defaultComboBoxModel1.addElement("Green");
        defaultComboBoxModel1.addElement("Yellow");
        comboBox1.setModel(defaultComboBoxModel1);
        startPanel.add(comboBox1, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setText("Quitter");
        startPanel.add(exitButton, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Title = new JLabel();
        Title.setIcon(new ImageIcon(getClass().getResource("/Trash_Hunter_200.png")));
        Title.setText("");
        startPanel.add(Title, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        startPanel.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        startPanel.add(spacer2, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return startPanel;
    }

}
