package game;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import game.asciiPanel.AsciiFont;
import game.asciiPanel.AsciiPanel;
import javafx.event.ActionEvent;

import game.com.anish.calabashbros.World;
import game.com.anish.screen.Screen;
import game.com.anish.screen.WorldScreen;

public class Main extends JFrame implements KeyListener{

    private AsciiPanel terminal;
    private JButton newgameButton;
    private JButton continueButton;
    private JButton playbackButton;
    private JPanel panel;
    private Screen screen;

    public Main() {
        super();
        panel = new JPanel();
        this.setSize(200, 200);
        newgameButton = new JButton("start new game");
        newgameButton.addActionListener(new StartGameAction(this));
        playbackButton = new JButton("watch playback");
        playbackButton.addActionListener(new PlaybackAction(this));
        panel.add(newgameButton);
        panel.add(playbackButton);
        add(panel);

        //add(terminal);
        //pack();
        //screen = new WorldScreen();
        //addKeyListener(this);
        //repaint();
        //new Thread(new Refresh(this)).start();
    }

    public JPanel getPanel() {
        return panel;
    }

    public void startGame() {
        File file = new File("log.txt");
        if(file.exists()) {
            file.delete();
        }
        terminal = new AsciiPanel(World.WIDTH, World.HEIGHT, AsciiFont.TALRYTH_32_32);
        add(terminal);
        pack();
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();
        addKeyListener(this);
        screen = new WorldScreen();
        repaint();
        new Thread(new Refresh(this)).start();
    }

    public void playback() {
        terminal = new AsciiPanel(World.WIDTH, World.HEIGHT, AsciiFont.TALRYTH_32_32);
        add(terminal);
        pack();
        File file = new File("log.txt");
        if(!file.exists()) {
            return;
        }
        //screen = new WorldScreen();
        //repaint();
        new Thread(new RefreshLog(this)).start();
    }

    public Screen getScreen() {
        return screen;
    }
    
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("key");
        screen = screen.respondToUserInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        Main app = new Main();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

}

class StartGameAction implements ActionListener {

    private Main M;

    public StartGameAction(Main M) {
        this.M = M;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        M.remove(M.getPanel());
        M.startGame();
    }

}

class PlaybackAction implements ActionListener {

    private Main M;

    public PlaybackAction(Main M) {
        this.M = M;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        M.remove(M.getPanel());
        M.playback();
    }

}