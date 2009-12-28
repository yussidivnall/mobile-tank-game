import javax.microedition.m3g.Sprite3D;
import javax.microedition.m3g.*;
public class TGameExplosionThread extends Thread{
	final int FRAME_WIDTH=100;
	final int IMAGE_HEIGHT=100;
	final int IMAGE_WIDTH = 1700;
	final int delay=50;
	Sprite3D mySprite;	
	Group exp_group; // needed to remove group when animation done  quite ugly!!!
	World my_world;	
	
	TGameExplosionThread(Sprite3D exp,Group eg,World mw){
		mySprite = exp;
		my_world=mw;		
		exp_group=eg;
		mySprite.setCrop(0,0,FRAME_WIDTH,IMAGE_HEIGHT);
		//mySprite.setAlphaThreshold(1);
		
		/*
		CompositingMode cm = new CompositingMode();
		cm.setBlending(CompositingMode.ALPHA);
		Appearance ap= new Appearance();
		ap.setCompositingMode(cm);
		mySprite.setAppearance(ap);
		*/
	}
	public void run(){
	try{
			int num_frames=IMAGE_WIDTH/FRAME_WIDTH;
			for(int i=0;i<num_frames;i++){
						System.out.println("Frame:"+i);
						mySprite.setCrop(i*100,0,FRAME_WIDTH,IMAGE_HEIGHT);
						this.sleep(delay);
			}
		}catch(InterruptedException ItrExp){
			ItrExp.printStackTrace();		
		}
		//mySprite=null;
		my_world.removeChild(exp_group);
	}	
	

}