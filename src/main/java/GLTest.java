package main.java;

import javax.swing.SwingUtilities;

import main.java.tcu.physics.test.*;

public class GLTest {
	public static void main(String[] args){
	    System.out.println("Running Graphics Library Test...");
	    //	    System.setProperty("sun.java2d.opengl", "true");   // Hardware acceleration?
	    SwingUtilities.invokeLater(new Runnable(){
		    public void run(){
			new GLTestFrame();
    		    }
		});
	}
}
