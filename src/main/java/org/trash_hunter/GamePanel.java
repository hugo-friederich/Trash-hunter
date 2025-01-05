package org.trash_hunter;

import org.trash_hunter.user.DiverDAO;
import org.trash_hunter.util.DatabaseConnection;
import org.trash_hunter.windows.Start_window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.SQLException;


public class GamePanel extends JFrame implements KeyListener, ActionListener, WindowListener {
    private BufferedImage backgroundImage;
    private Graphics2D contexte;
    private JLabel jLabel1;
    private Game game;
    private Timer timer;
    private JDialog diversDialog;
    private boolean isRPressed = false; //

    public GamePanel(String pseudo, String color) throws SQLException{
        //init de la fenetre
        this.setSize(1440,780);
        this.setResizable(false);
        this.setTitle("Trash Hunter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new Dimension(1440,780));
        this.setContentPane(this.jLabel1);
        //this.setFocusableWindowState(false);  // Empêche l'interraction avec la fenêtre
        this.pack();

        // Création du buffer pour l'affichage du jeu et récupération du contexte graphique
        this.backgroundImage = new BufferedImage(1440, 780, BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(backgroundImage));
        this.contexte = this.backgroundImage.createGraphics();

        //Creation du jeu
        this.game = new Game(pseudo,color);

        //Creation  du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer=new Timer(20,this);
        this.timer.start();

        //Ajout du listener
        this.addKeyListener(this);
        this.addWindowListener(this);
    }
    public static void main (String[]args) throws SQLException {
        Start_window startWindow = new Start_window();   //Le GamePanel se lance dans ActionPerformed de la classe Start_window
    }
    private void showDiversTable() {
        try {
            // Connexion à la base de données
            Connection connection = DatabaseConnection.getConnection();
            DiverDAO diverDAO = new DiverDAO(connection);

            // Récupérer les résultats sous forme de chaîne
            String resultString = diverDAO.showDiversAsString();

            // Créer un JTextArea pour afficher les résultats
            JTextArea textArea = new JTextArea(resultString);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            // Création de la fenêtre de dialogue
            diversDialog = new JDialog(this, "Joueurs actuels", Dialog.ModalityType.APPLICATION_MODAL);
            diversDialog.setSize(600, 300);
            diversDialog.setLocationRelativeTo(this);
            diversDialog.add(new JScrollPane(textArea)); // Ajoute le JTextArea dans le JDialog
            diversDialog.setVisible(true); // Affiche le JDialog

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des données : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void keyPressed (KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_Z ||evt.getKeyCode() == KeyEvent.VK_UP) {
            this.game.getMyAvatar().setUp(true);
        } else if (evt.getKeyCode() == KeyEvent.VK_S || evt.getKeyCode()== KeyEvent.VK_DOWN) {
            this.game.getMyAvatar().setDown(true);
        } else if (evt.getKeyCode() == KeyEvent.VK_Q|| evt.getKeyCode()== KeyEvent.VK_LEFT) {
            this.game.getMyAvatar().setLeft(true);
        } else if (evt.getKeyCode() == KeyEvent.VK_D || evt.getKeyCode()== KeyEvent.VK_RIGHT) {
            this.game.getMyAvatar().setRight(true);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {  // Ajout de la touche Échap
            int choix = JOptionPane.showConfirmDialog(this,
                    "Voulez-vous vraiment quitter le jeu ?",
                    "Quitter le jeu",
                    JOptionPane.YES_NO_OPTION);

            if (choix == JOptionPane.YES_OPTION) {
                //Action à venir effectuer avant la fermeture de la fenêtre
                this.game.getDiverDAO().delete(this.game.getMyAvatar().getId());
                this.game.getDiverDAO().addToBestScores(this.game.getDiver());
                DatabaseConnection.close();  // Arrête la conncetion à la base de donnée
                this.dispose(); //Ferme la fenêtre
                System.exit(0);
            }

        }
        if (evt.getKeyCode() == KeyEvent.VK_R) {    //Affiche le tableau des joueurs actuels lors de l'appui sur R
            if (!isRPressed) { // Si R n'est pas déjà enfoncé
                isRPressed = true; // R est enfoncé
                showDiversTable(); // Afficher le tableau des scores actuel
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_Z ||evt.getKeyCode() == KeyEvent.VK_UP) {
            this.game.getMyAvatar().setUp(false);
        } else if (evt.getKeyCode() == KeyEvent.VK_S || evt.getKeyCode()== KeyEvent.VK_DOWN) {
            this.game.getMyAvatar().setDown(false);
        } else if (evt.getKeyCode() == KeyEvent.VK_Q || evt.getKeyCode()== KeyEvent.VK_LEFT) {
            this.game.getMyAvatar().setLeft(false);
        } else if (evt.getKeyCode() == KeyEvent.VK_D || evt.getKeyCode()== KeyEvent.VK_RIGHT) {
            this.game.getMyAvatar().setRight(false);
        }
        if (evt.getKeyCode() == KeyEvent.VK_R) {
            isRPressed = false; // Marquez R comme relâché
            diversDialog.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public void stopTheGame(){
        this.game.getDiverDAO().delete(this.game.getDiver().getId());       //supprime le joueur de la table diver local
        this.game.getDiverDAO().addToBestScores(this.game.getDiver());      //ajoute le joueur à la table des meilleurs scores
        if (this.game.getDiverDAO().findAll().isEmpty()){                   //efface les déchets pour le dernier joueur partant du serveur
            this.game.getTrashDAO().clear();
        }
        System.exit(1);
    }

    //A modifier pour que le jeu se relance -> probleme avec HikariPool request time
    public void gameIsOver(){
        try {
            Start_window startWindow = new Start_window();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.game.update();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        this.game.rendering(contexte);
        this.jLabel1.repaint();
        if (this.game.isFinished()) {
            stopTheGame();
        }
    }
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        stopTheGame();
    }

    @Override
    public void windowClosed(WindowEvent e) {
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


    public Graphics2D getContexte() {
        return contexte;
    }
}

