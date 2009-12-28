import javax.microedition.m3g.*;
public class TSprite extends TAnimation{
	Sprite3D sprite;
	int frame_width;
	TPoint position;	
	int width,height;	
	int delay;	
	int time_last_frame=-1;	

	
	int PLAY_ONCE=1;
	int LOOP=2;
	int LOOP_BACK=3;	
	int state=0;
	
	int frame=0;
	int num_frames=0;
	TSprite(Sprite3D s,TPoint p,int w,int d,int m){
		sprite=s;
		position=p;
		frame_width=w;
		delay=d;
		state=m;
		
		sprite.translate(position.X,position.Y,position.Z);
		width=sprite.getImage().getWidth();
		height=sprite.getImage().getHeight();
		num_frames=width/frame_width;
		
		group.addChild(sprite);
	}
	
	public void advance(int t){
			if(time_last_frame==-1)time_last_frame=t;
			if(time_last_frame+delay < t){
				time_last_frame=t;				
				update();
			}
	}
	public void update(){
			if(state==PLAY_ONCE){
				sprite.setCrop(frame*frame_width,0,frame_width,height);
				frame++;
				if(frame>num_frames)destroy=true;	
			}
	}
}