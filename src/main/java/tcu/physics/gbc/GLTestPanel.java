package main.java.tcu.physics.gbc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import main.java.tcu.physics.lib3d.*;

/**
 * 3D GRAPHICS ENGINE - using Java2D...
 * Cole H Turner
 */ 
public class GLTestPanel extends JPanel {

    private static final long serialVersionUID = -6817320406620056094L;
    
    private GL3D g3d;

    private double pitch, yaw;

    // test variables
    private GLVec front, side;
    private GLVec pos, vel;
    private GLPoly[] polys;

    private Color white, gray;
    
    public GLTestPanel(){
	vel = new GLVec();
	pos = new GLVec(-2, -2, 1.5);
	front = new GLVec();
	side = new GLVec();
	yaw = -45;
	pitch = 75;
	int npolys = 50;
	polys = new GLPoly[npolys];
        double a, inc = 360.0 / npolys;
	for(int i = 0; i < npolys; i++){
	    a = inc * i;
	    polys[i] = new GLPoly(new GLVec(),
				  new GLVec(Math.cos(Math.toRadians(a)), Math.sin(Math.toRadians(a)), 1),
				  new GLVec(Math.cos(Math.toRadians(a+inc)), Math.sin(Math.toRadians(a+inc)), 1));
	}
	white = new Color(255, 255, 255, 80);
	gray = new Color(155, 155, 155, 80);
	setSize(GLTestFrame.SCREEN_SIZE);
	g3d = new GL3D();
	g3d.setRotation(Math.toRadians(yaw), Math.toRadians(pitch), 0.0);
	g3d.setView(getWidth(), getHeight(), Math.toRadians(90));
	g3d.setLocation(pos);	
    }
    
    @Override
    protected void paintComponent(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	g2d.setColor(Color.BLACK);
	g2d.fillRect(0, 0, getWidth(), getHeight());
	g2d.translate(getWidth() / 2, getHeight() / 2);

	g3d.setGraphics(g2d);

	front.setVec(GLVec.perp(GLVec.Z, g3d.getFace()).getUnit());
	side.setVec(GLVec.perp(GLVec.Z, g3d.getRight()).getUnit());
	
	// GRID...
	g3d.setColor(white);
	for(GLPoly p : polys){
	    g3d.fillPolygon(p);
	    g3d.setColor(g3d.getColor().equals(white) ? gray : white);
	}
	
       	pos.add(GLVec.scale(vel, 0.1));
    }

    public void test(){
	System.exit(0);
    }
    
    public void keyPressed(KeyEvent e){
	switch(e.getKeyCode()){
	case KeyEvent.VK_SPACE :
	    vel.add(GLVec.sum(GLVec.Z, GLVec.proj(GLVec.Z, vel).getNegative()));					       
	    break;
	case KeyEvent.VK_SHIFT :
	    vel.add(GLVec.sum(GLVec.proj(GLVec.Z, vel), GLVec.Z).getNegative());
	    break;
	case KeyEvent.VK_W :
	    vel.add(GLVec.sum(front, GLVec.proj(front, vel).getNegative()));	    
	    break;
	case KeyEvent.VK_A :
	    vel.add(GLVec.sum(GLVec.proj(side, vel), side).getNegative());
	    break;
	case KeyEvent.VK_S :
	    vel.add(GLVec.sum(GLVec.proj(front, vel), front).getNegative());
	    break;
	case KeyEvent.VK_D :
	    vel.add(GLVec.sum(side, GLVec.proj(side, vel).getNegative()));	    
	    break;
	case KeyEvent.VK_UP :
	    pitch++;
	    break;
	case KeyEvent.VK_DOWN :
	    pitch--;
	    break;
	case KeyEvent.VK_LEFT :
	    yaw++;
	    break;
	case KeyEvent.VK_RIGHT :
	    yaw--;
	    break;
	}
    }

    public void keyReleased(KeyEvent e){
	switch(e.getKeyCode()){
	case KeyEvent.VK_SPACE :
	case KeyEvent.VK_SHIFT :
	    vel.add(GLVec.proj(GLVec.Z, vel).getNegative());
	    break;
	case KeyEvent.VK_W :
	case KeyEvent.VK_S :
	    vel.add(GLVec.proj(front, vel).getNegative());
	    break;
	case KeyEvent.VK_A :
	case KeyEvent.VK_D :
	    vel.add(GLVec.proj(side, vel).getNegative());
	    break;
	}
    }
}
