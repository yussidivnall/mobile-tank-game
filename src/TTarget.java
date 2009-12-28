import javax.microedition.m3g.*;
public class TTarget{
	Group group;
	TPoint source;
	TPoint destination;	
	TControl control;
	
	boolean moving=true;	
	
	float min_speed=0.1f;
	float max_speed=3.0f;
	float speed=0.5f; // default speed	
	
	int hit_points=10;
	int value=50;
	float time;
	boolean animating = false; // I'd rather do this with the m3g animation, but can't get it right!
	
	TTarget(Group g,TPoint src,TPoint dest){
		group=(Group)g.duplicate();
		source=src;
		destination=dest;		
		//test_anim();
		//init_anims();
	}
	public void setControl(TControl c){
		control=c;	
	}
	public void setSpeed(float s){
		speed=s;	
	}	
	
	public void advance(float t){
		time=t;
		//if(animating) animate();
		if(moving)control.advance(speed);
	}
	public void shot(){
		//
		System.out.println("Im hit");
		//shake_track.getKeyframeSequance().		
				
		hit_points--;
	}	
	
	
	
	
//--------------

	//AnimationController anim_ctl;
	AnimationTrack shake_track;
	public void init_anims(){
		KeyframeSequence shot_seq = new KeyframeSequence(3,4,KeyframeSequence.LINEAR);
		shot_seq.setKeyframe(0,0,new float[]{0,1,0,1f});
		shot_seq.setKeyframe(1,500,new float[]{0,1f,0,2.25f});
		shot_seq.setKeyframe(2,1000,new float[]{0,1,0,1f});
		shot_seq.setDuration(1500);
		AnimationTrack shot_track= new AnimationTrack(shot_seq,AnimationTrack.ORIENTATION);
		//group.addAnimationTrack(shot_track);
		AnimationController shot_ctl = new AnimationController();	
		
		//AnimationController c = shot_track
		shot_track.setController(shot_ctl);
		shake_track=shot_track;
		//anim_ctl = shot_ctl;
	}	
	
	
	
	public void test_anim(){
		KeyframeSequence seq = new KeyframeSequence(2,3,KeyframeSequence.LINEAR);
		seq.setKeyframe(0,0,new float[]{0f,1f,0f});
		seq.setKeyframe(1,500,new float[]{0f,0f,0f});		
		seq.setDuration(1000);
		
		AnimationTrack trk = new AnimationTrack(seq,AnimationTrack.TRANSLATION);

		Object3D o = group.getChild(0);
		o.addAnimationTrack(trk);

		AnimationController ctl = new AnimationController();
			 		
		trk.setController(ctl);
		ctl.setActiveInterval(0,5000);
		ctl.setPosition(0,2000);
	}
	
}