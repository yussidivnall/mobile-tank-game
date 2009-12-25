import javax.microedition.m3g.*;
public class TTarget{
	Group group;
	TPoint source;
	TPoint destination;	
	TControl control;
	
	boolean moving=true;	
	float speed=0.5f;	
	int hit_points=10;
	
	TTarget(Group g,TPoint src,TPoint dest){
		group=(Group)g.duplicate();
		source=src;
		destination=dest;
	}
	public void setControl(TControl c){
		control=c;	
	}
	public void advance(){
		if(moving)control.advance(speed);
	}
	public void shot(){
		System.out.println("Im hit");
		hit_points--;	
	}	
	
	
}