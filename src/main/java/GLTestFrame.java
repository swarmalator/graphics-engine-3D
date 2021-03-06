package main.java;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;

public class GLTestFrame extends JFrame implements KeyListener {
    
    private static final long serialVersionUID = -8880493733813793551L;

    /**
     * 3D Gravitational Billiard in a Cone
     * Java Demonstration
     * Version 4, Started 4/26/2017
     * @author Cole H Turner
     */    
    
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    
    public static final int SCREEN_RESOLUTION = Toolkit.getDefaultToolkit().getScreenResolution();
    
    private static Timer timer;

    private long currentTime;
    
    private GLTestPanel sim;
    
    public GLTestFrame() {
	super("3D-GBC");
	initSim();
	initFrame();
    }
    
    public void update(double d) {
	
    }

    public void play() {

    }

    public void pause() {
	
    }

    public void restart() {
	
    }

    public void keyPressed(KeyEvent e) {
	if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	    System.exit(0);
	}
	sim.keyPressed(e);
    }
    
    public void initSim() {
	sim = new GLTestPanel();
	currentTime = System.currentTimeMillis();
	timer = new Timer(16, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    long l = System.currentTimeMillis();
		    update((double) (l - currentTime) / 1000.0);
		    currentTime = l;
		    repaint();
		}
	    });
	timer.start();
    }

    public void initFrame() {
	int cenx, ceny;
	cenx = (int)(SCREEN_SIZE.getWidth()) / 2;
	ceny = (int)(SCREEN_SIZE.getHeight()) / 2;      

	Container pane = this.getContentPane();
	sim.setPreferredSize(SCREEN_SIZE);
	pane.add(sim);
	setUndecorated(true);
	
	pack();
	setLocation(cenx - getWidth() / 2, ceny - getHeight() / 2);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setVisible(true);
	addKeyListener(this);
	setBackground(Color.BLACK);

	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    
    public void keyReleased(KeyEvent e) {sim.keyReleased(e);}
    public void keyTyped(KeyEvent e) {}

}











