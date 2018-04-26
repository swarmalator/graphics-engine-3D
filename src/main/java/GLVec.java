package main.java;

public class GLVec extends GLQuad {

    public static final GLVec X = new GLVec(1, 0, 0);
    public static final GLVec Y = new GLVec(0, 1, 0);
    public static final GLVec Z = new GLVec(0, 0, 1);
    
    // CONSTRUCTORS...
    
    public GLVec(double x, double y, double z){
	super(0, x, y, z);
    }

    public GLVec(double[] v){
	super(v);
    }

    public GLVec(GLQuad q){
	super(q.getVec());
    }

    public GLVec(){
	super(0, 0, 0, 0);
    }

    public void add(GLVec v){
	setVec(sum(this, v));
    }

    // MATH...
    
    public static GLVec proj(GLVec v1, GLVec v2){
	return scale(v1, dot(v1, v2) / dot(v1, v1));
    }

    public static GLVec perp(GLVec v1, GLVec v2){
	return sum(v2, proj(v1, v2).getNegative());
    }

    public static GLVec rotate(GLVec v, GLQuad q){
	return mult(q, v, q.getInverse()).toVector();
    }

    public static GLVec scale(GLVec v, double d){
	return GLQuad.scale(v, d).toVector();
    }
    
    public static double dot(GLVec v1, GLVec v2){
	return GLQuad.dot(v1, v2);
    }

    public static GLVec cross(GLVec v1, GLVec v2){
	return new GLVec(cross(v1.getVec(), v2.getVec()));
    }

    public static GLVec sum(GLVec v1, GLVec v2){
	return new GLVec(sum(v1.getVec(), v2.getVec()));
    }

    // PRIVATE STATIC MATH...

    private static double[] cross(double[] v1, double[] v2){
	return new double[]{0,
			    v1[2] * v2[3] - v1[3] * v2[2],
			    v1[3] * v2[1] - v1[1] * v2[3],
			    v1[1] * v2[2] - v1[2] * v2[1]
	};
    }

    private static double[] sum(double[] v1, double[] v2){
	return new double[]{0,
			    v1[1] + v2[1],
			    v1[2] + v2[2],
			    v1[3] + v2[3]
	};
    }
    
    // GETTERS + SETTERS

    public GLVec getNegative(){
	return scale(this, -1);
    }

    public double getLength(){
	return Math.sqrt(dot(this, this));
    }

    public GLVec getUnit(){
	return scale(this, 1.0 / getLength());
    }

}
