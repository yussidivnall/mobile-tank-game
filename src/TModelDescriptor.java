import javax.microedition.m3g.*;
//Describes game's models
public class TModelDescriptor{
	Group myGroup;
	Group A,B,C,D; // corners from top	
	// A-B
	// C-D
	float width,height,length;
	TModelDescriptor(Group g,float w,float h,float l){
		myGroup=g;
		width=w;height=h;length=l;
		A = new Group();B = new Group();C = new Group();D = new Group();
		myGroup.addChild(A);myGroup.addChild(B);myGroup.addChild(C);myGroup.addChild(D);
		A.translate(w/2,h,l/2);
		B.translate(-w/2,h,l/2);
		C.translate(w/2,h,-l/2);
		D.translate(-w/2,h,-l/2);
	}
	public float getLength(){
		return length;	
	}
}