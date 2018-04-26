
package main.java;

public class GLQuad {

    protected double[] comp;

    public GLQuad(double c0, double c1, double c2, double c3){
	setQuad(c0, c1, c2, c3);
    }
    
    public GLQuad(double[] q){
	setQuad(q);
    }

    public GLQuad(){
	setQuad(1, 0, 0, 0);
    }

    // MATH...

    public GLQuad getConjugate(){
	return new GLQuad(comp[0], -comp[1], -comp[2], -comp[3]);
    }
    
    public GLQuad getInverse(){
	return scale(getConjugate(), 1.0 / dot(this, this));
    }

    // GETTERS + SETTERS...
    
    public double[] getQuad(){
	return comp;
    }

    public double[] getVec(){
	return new double[]{0, comp[1], comp[2], comp[3]};
    }
    
    public void setQuad(double c0, double c1, double c2, double c3){
	comp = new double[]{c0, c1, c2, c3};
    }

    public void setQuad(double[] q){
	comp = q;
    }

    public void setQuad(GLQuad q){
	setQuad(q.getQuad());
    }

    public void setVec(double c1, double c2, double c3){
	comp[1] = c1;
	comp[2] = c2;
	comp[3] = c3;
    }

    public void setVec(double[] v){
	setVec(v[1], v[2], v[3]);
    }

    public void setVec(GLVec q){
	setVec(q.getVec());
    }

    public GLVec toVector(){
	return new GLVec(getVec());
    }

    @Override
    public String toString(){
	return "GLQuad: " + comp[0] + " <" + comp[1] + ", " + comp[2] + ", " + comp[3] + ">";
    }

    // STATIC MATH...

    public static GLQuad scale(GLQuad q, double d){
	return new GLQuad(scale(q.getQuad(), d));
    }
    
    public static double dot(GLQuad q1, GLQuad q2){
	return dot(q1.getQuad(), q2.getQuad());
    }

    public static GLQuad mult(GLQuad... qs){
	GLQuad q = new GLQuad();
	for(int i = 0; i < qs.length; i++){
	    q.setQuad(mult(q.getQuad(), qs[i].getQuad()));
	}
	return q;
    }

    public static GLQuad create(GLVec a, double rad){
	GLQuad q = new GLQuad(Math.cos(rad / 2), 0, 0, 0);
	q.setVec(GLVec.scale(a.getUnit(), Math.sin(rad / 2)));
	return q;
    }

    // PRIVATE STATIC MATH...

    public static double[] scale(double[] q, double d){
	return new double[]{q[0] * d,
			    q[1] * d,
			    q[2] * d,
			    q[3] * d};
    }
    
    public static double dot(double[] q1, double[] q2){
	return q1[0] * q2[0] + q1[1] * q2[1] + q1[2] * q2[2] + q1[3] * q2[3];
    }

    private static double[] mult(double[] q1, double[] q2){
	return new double[]{
	    q1[0] * q2[0] - q1[1] * q2[1] - q1[2] * q2[2] - q1[3] * q2[3],
	    q1[0] * q2[1] + q1[1] * q2[0] + q1[2] * q2[3] - q1[3] * q2[2],
	    q1[0] * q2[2] - q1[1] * q2[3] + q1[2] * q2[0] + q1[3] * q2[1],
	    q1[0] * q2[3] + q1[1] * q2[2] - q1[2] * q2[1] + q1[3] * q2[0]
	};
    }
}
