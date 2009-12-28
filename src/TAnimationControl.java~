import java.util.*;
import javax.microedition.m3g.*;
public class TAnimationControl{
	Vector anims;
	World world;	
	
	TAnimationControl(World w){
		anims=new Vector();	
	}
	public void addSprite(Sprite3D s,TPoint p,int w,int d,int m){
		//args: Sprite, position,frame width,delay,playback mode
		TSprite sprt = new TSprite(s,p,w,d,m);
		world.addChild(sprt.group);
		anims.addElement(sprt);	
	}
	public void advance(int t){
			for (Enumeration e=anims.elements();e.hasMoreElements();){
				TAnimation a = (TAnimation)e.nextElement();
				if(a.destroy){
					world.removeChild(a.group);
					anims.removeElement(a);
				}else{
					a.advance(t);				
				}
			}
	}
	
}