package main.java;

public class GLCurve {

    private GLVec[] vec;
    
    public GLCurve(GLVec... vecs){
	vec = vecs;
    }

    public GLVec[] getVertices(){
	return vec;
    }
}
