package org.trash_hunter;

import org.trash_hunter.util.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;


public class GamePanel extends JFrame implements KeyListener, ActionListener, WindowListener {
    private BufferedImage backgroundImage;
    private Graphics2D contexte;
    private JLabel jLabel1;
    private Game game;
    private Timer timer;

    public GamePanel() throws SQLException {
        //init de la fenetre
        this.setSize(1440,780);
        this.setResizable(false);
        this.setTitle("org.trash_hunter.trashes.Trash Hunter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new Dimension(1440,780));
        this.setContentPane(this.jLabel1);
        this.pack();

        //Creation du jeu
        this.game=new Game();

        // Création du buffer pour l'affichage du jeu et récupération du contexte graphique
        this.backgroundImage = new BufferedImage(1440, 780, BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(backgroundImage));
        this.contexte = this.backgroundImage.createGraphics();

        //Creation  du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer=new Timer(20,this);
        this.timer.start();

        //Ajout du listener
        this.addKeyListener(this);
        this.addWindowListener(this);
    }
    public static void main (String[]args) throws SQLException {
        GamePanel panel=new GamePanel();
        panel.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 87 ||evt.getKeyCode() == KeyEvent.VK_UP) {
            this.game.getDiver().setUp(true);
        } else if (evt.getKeyCode() == 83 || evt.getKeyCode()== KeyEvent.VK_DOWN) {
            this.game.getDiver().setDown(true);
        } else if (evt.getKeyCode() == 65|| evt.getKeyCode()== KeyEvent.VK_LEFT) {
            this.game.getDiver().setLeft(true);
        } else if (evt.getKeyCode() == 68 || evt.getKeyCode()== KeyEvent.VK_RIGHT) {
            this.game.getDiver().setRight(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == 87 ||evt.getKeyCode() == KeyEvent.VK_UP) {
            this.game.getDiver().setUp(false);
        } else if (evt.getKeyCode() == 83 || evt.getKeyCode()== KeyEvent.VK_DOWN) {
            this.game.getDiver().setDown(false);
        } else if (evt.getKeyCode() == 65|| evt.getKeyCode()== KeyEvent.VK_LEFT) {
            this.game.getDiver().setLeft(false);
        } else if (evt.getKeyCode() == 68 || evt.getKeyCode()== KeyEvent.VK_RIGHT) {
            this.game.getDiver().setRight(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        this.game.update();
        this.game.rending(contexte);
        this.jLabel1.repaint();
        if (this.game.isFinished()) {
            this.timer.stop();
        }
    }
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        //Action à venir effectuer avant la fermeture de la fenêtre
        this.game.getDiverDAO().delete(this.game.getDiver().getId());
        this.game.getDiverDAO().addToBestScores(this.game.getDiver());
        DatabaseConnection.close();  // Arrête la conncetion à la base de donnée
        this.dispose(); //Ferme la fenêtre
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
}
