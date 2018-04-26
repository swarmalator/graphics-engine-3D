package main.java;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

public class GL3D extends Graphics {

    public double w, e, a, fov;
    private Graphics2D g2d;
    private GLVec loc, up, face, right;
    private GLQuad rot;

    public GL3D(){
	loc = new GLVec();
	up = new GLVec();
	face = new GLVec();
	right = new GLVec();
	rot = new GLQuad();
    }

    public void setGraphics(Graphics2D g){
	g2d = g;
    }
    
    // MATH...
    
    public void drawLine(GLVec v1, GLVec v2){
	Point p1 = getProjection(v1);
	Point p2 = getProjection(v2);
	if(p1 != null && p2 != null) drawLine(p1.x, p1.y, p2.x, p2.y);
    }

    public void fillPolygon(GLPoly poly){
	GLVec[] vs = poly.getVertices();
	int n = vs.length;
	int[] xs = new int[n];
	int[] ys = new int[n];
	Point p;
	for(int i = 0; i < n; i++){
	    p = getProjection(vs[i]);
	    if(p == null) return;
	    xs[i] = p.x;
	    ys[i] = p.y;
	}
	fillPolygon(xs, ys, n);
    }
    
    // GETTERS AND SETTERS...

    public void setRotation(double yaw, double pitch, double roll){
	GLVec x = new GLVec(GLVec.X);
	GLVec y = new GLVec(GLVec.Y);
	GLVec z = new GLVec(GLVec.Z);
	GLQuad q1 = GLQuad.create(z, yaw);
	x.setVec(GLVec.rotate(x, q1));
	y.setVec(GLVec.rotate(y, q1));
	GLQuad q2 = GLQuad.create(x, pitch);
	y.setVec(GLVec.rotate(y, q2));
	z.setVec(GLVec.rotate(z, q2));
	GLQuad q3 = GLQuad.create(z, roll);
	x.setVec(GLVec.rotate(x, q3));
	y.setVec(GLVec.rotate(y, q3));
	right.setVec(x);
	up.setVec(GLVec.proj(GLVec.Z, y).getUnit());
	face.setVec(z.getNegative());
	rot.setQuad(GLQuad.mult(q3, q2, q1));
    }
    
    public GLQuad getRotation(){
	return rot;
    }

    public GLVec getUp(){
	return up;
    }

    public GLVec getFace(){
	return face;
    }

    public GLVec getRight(){
	return right;
    }
    
    public GLVec getRelative(GLVec v){
	return GLVec.rotate(GLVec.sum(v, loc.getNegative()), rot.getInverse());
    }
    
    public Point getProjection(GLVec v){
	double[] p = getRelative(v).getVec();
	if(p[3] > 0) return null;
	return new Point((int)(w * e * p[1] / -p[3]), -(int)(w * e * p[2] / -p[3]));
    }
    
    public void setView(double width, double height, double fov){
	this.fov = fov;
	w = width;
	a = height / width;
	e = 1.0 / Math.tan(fov / 2);
    }

    public GLVec getLocation(){
	return loc;
    }

    public void setLocation(double x, double y, double z){
	loc = new GLVec(x, y, z);
    }
    
    public void setLocation(GLVec v){
	loc = v;
    }

    // 2D GRAPHICS FUNCTIONS
    @Override
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {g2d.drawPolygon(xPoints, yPoints, nPoints);}    
    @Override
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {g2d.fillPolygon(xPoints, yPoints, nPoints);}    
    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {g2d.drawLine(x1, y1, x2, y2);}
    @Override
    public void setColor(Color c) { g2d.setColor(c); }    
    @Override
    public Color getColor() { return g2d.getColor(); }

    // UNIMPLEMENTED...
    
    @Override
    public void clearRect(int x, int y, int width, int height) {}
    @Override
    public void clipRect(int x, int y, int width, int height) {}
    @Override
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {}
    @Override
    public Graphics create() { return null; }
    @Override
    public void dispose() {}
    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {}
    @Override
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) { return false; }
    @Override
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) { return false; }
    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) { return false; }
    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) { return false; }
    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			ImageObserver observer) { return false; }
    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			Color bgcolor, ImageObserver observer) {  return false; }
    @Override
    public void drawOval(int x, int y, int width, int height) {}
    @Override
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {}
    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}
    @Override
    public void drawString(String str, int x, int y) {}
    @Override
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {}
    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {}
    @Override
    public void fillOval(int x, int y, int width, int height) {}
    @Override
    public void fillRect(int x, int y, int width, int height) {}
    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}
    @Override
    public Shape getClip() { return null; }
    @Override
    public Rectangle getClipBounds() { return null; }
    @Override
    public Font getFont() { return null; }
    @Override
    public FontMetrics getFontMetrics(Font f) { return null; }
    @Override
    public void setClip(Shape clip) {}
    @Override
    public void setClip(int x, int y, int width, int height) {}
    @Override
    public void setFont(Font font) {}
    @Override
    public void setPaintMode() {}
    @Override
    public void setXORMode(Color c1) {}
    @Override
    public void translate(int x, int y) {}

}
