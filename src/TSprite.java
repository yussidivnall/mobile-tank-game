import javax.microedition.m3g.*;
public class TSprite extends TAnimation{
	Sprite3D sprite;
	int frame_width;
	TPoint position;	
	int width,height;	
	int delay;	
	long time_last_frame=-1;	

	
	static final int PLAY_ONCE=1;
	static final int LOOP=2;
	static final int LOOP_BACK=3;	
	int state=0;
	
	int frame=0;
	int num_frames=0;
	
	TSprite(Sprite3D s,Group g,int w,int d,int m){
		sprite=s;
		frame_width=w;
		delay=d;
		state=m;

		width=sprite.getImage().getWidth();
		height=sprite.getImage().getHeight();
		num_frames=width/frame_width;
		sprite.setCrop(0,0,100,100);
		g.addChild(sprite);
		group.setTranslation(0,0,0);
		group.addChild(g);
	}
	
	TSprite(Sprite3D s,TPoint p,int w,int d,int m){
		super();		
		System.out.println("TSprite constructor Started");		
		sprite=s;
		position=p;
		frame_width=w;
		delay=d;
		state=m;
		
		//sprite.translate(position.X,position.Y,position.Z);
		width=sprite.getImage().getWidth();
		height=sprite.getImage().getHeight();
		num_frames=width/frame_width;
		sprite.setCrop(0,0,100,100);
		group.addChild(sprite);
		System.out.println("Added sprite:"+sprite+" to group: "+group);
		group.setTranslation(position.X,position.Y,position.Z);		
		System.out.println("TSprite constructor ends");
	}
	
	public void advance(long t){
			//System.out.println("TSprite advance called at:"+t);
			if(time_last_frame==-1){
					time_last_frame=t;
					System.out.println("Start anim at:"+t);			
			}
			if((time_last_frame+delay) < t){
				time_last_frame=t;				
				update();
				//System.out.println("Updateing !!!!!!!!!!!!!!!!!");
			}
		//System.out.println("TSprite advance ends");
	}
	public void update(){
			System.out.println("frame:"+frame);
			if(state==PLAY_ONCE){
				sprite.setCrop(frame*frame_width,0,frame_width,height);
				frame++;
				if(frame>num_frames)destroy=true;	
			}
	}
}