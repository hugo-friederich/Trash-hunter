package org.trash_hunter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;


public class GamePanel extends JFrame implements KeyListener, ActionListener {
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

        //Creation du buffer pour l'affichage du jeu et recuperation du contexte graphique
        this.backgroundImage=new BufferedImage(this.jLabel1.getWidth(),
                this.jLabel1.getHeight(),BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(backgroundImage));
        this.contexte=this.backgroundImage.createGraphics();

        //Creation du jeu
        this.game=new Game();

        //Creation  du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer=new Timer(20,this);
        this.timer.start();

        //Ajout du listener
        this.addKeyListener(this);
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
}
