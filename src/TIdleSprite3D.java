import javax.microedition.m3g.*;
//Encalsulates idle sprites (trees,flowers,whatever)
public class TIdleSprite3D{
	public TPoint translation;
	public Group group;	
	public TPoint scale;
	TIdleSprite3D(Group g,TPoint p,TPoint s){
		translation=p;
		group = g;
		scale = s;
	}
}