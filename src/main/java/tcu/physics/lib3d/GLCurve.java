package main.java.tcu.physics.lib3d;

public class GLCurve {

    private GLVec[] vec;
    
    public GLCurve(GLVec... vecs){
	vec = vecs;
    }

    public GLVec[] getVertices(){
	return vec;
    }
}
