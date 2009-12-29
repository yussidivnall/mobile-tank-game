import java.util.*;
import javax.microedition.m3g.*;
public class TAnimationControl{
	Vector anims;
	World world;	
	
	TAnimationControl(World w){
		world = w;
		anims=new Vector();	
	}
	
	public void addSprite(Sprite3D s,Group g,int w,int d,int m){
		TSprite sprt = new TSprite(s,g,w,d,m);
		world.addChild(sprt.group);	
		anims.addElement(sprt);	
	}	
	
	
	public void addSprite(Sprite3D s,TPoint p,int w,int d,int m){
		//args: Sprite, position,frame width,delay,playback mode
		TSprite sprt = new TSprite(s,p,w,d,m);		
		world.addChild(sprt.group);
		//System.out.println("Added group :"+sprt.group+" to world: "+world);
		//sprt.group.setTranslation(p.X,p.Y,p.Z);
		//sprt.sprite.setCrop(0,0,100,100);		
		//sprt.group.scale(10,10,10);		
		anims.addElement(sprt);
	}
	public void advance(long t){
			if(anims.size()<=0) return;
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