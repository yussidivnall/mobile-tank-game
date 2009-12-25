import java.util.Random;
import java.util.*;
import javax.microedition.m3g.*;

public class TGameAI{
	float distance_error=5;

	//Rulesets
	public final int STOP_THAT_TRUCK=1;

	//Spawn timing
	public final int AT_A_TIME=1;
	public final int EVERY=2;

	//Spawn locations
	public final int RANDOM_SOURCE=1;
	public final int SEQUNCE=2;	

	//Target destination
	public final int RANDOM_DESTINATION=1;	
	
	int rule_set=0;
	
	int spawn_rule=0; //AT_A_TIME or EVERY
	int spawn_timing=0;//number_of living trucks if AT_A_TIME, number of ticks if EVERY	
	int spawn_location=0;	

	int target_destination=0;


	int targets_alive=0;
	public static Random random;
	Level myLevel;
	
	TGameAI(Level level){
		myLevel=level;
		random=new Random();
	}
	public void load(String ai_file){
		TGameAIParser parser = new TGameAIParser(ai_file,this);
	}
	public void setRuleset(int r){
		rule_set=r;
		System.out.println("ruleset:"+r);	
	}
	public void setSpawnRule(int r,int t){
		spawn_rule=r;spawn_timing=t;	
	}
	public void setSpawnLocation(int l){
		spawn_location=l;	
	}
	public void setTargetDestination(int d){
		target_destination=d;	
	}	
	
//----------
	public void advance(){
		if(check_spawn()){
			System.out.println("Need to spawn");
			spawn();		
		}
		for(Enumeration e=myLevel.targets.elements();e.hasMoreElements();){
			TTarget t = (TTarget)e.nextElement();
			if(check_destination(t))t.moving=false;			
			t.advance();
			
		}		
		
		
	}
	public void spawn(){
		int num_src=myLevel.sources.size();int num_dest=myLevel.destinations.size();
		int src=0;int dest=0;
		int target_model=0;//should come from config
		if(spawn_location==RANDOM_SOURCE){
			if(num_src>1){src=Math.abs(random.nextInt())%num_src;}else{src=0;}
		}
		if(target_destination==RANDOM_DESTINATION){
			if(num_dest>1){dest=Math.abs(random.nextInt())%num_dest;}else{dest=0;}
		}
		TBuilding src_build=(TBuilding)myLevel.sources.get(new Integer(src));
		TPoint starting_point = src_build.translation;
		
		TBuilding dest_build=(TBuilding)myLevel.destinations.get(new Integer(dest));
		TPoint ending_point=dest_build.translation;		
				
		Group group=(Group)myLevel.trucks.get(new Integer(target_model));		
		TTarget target= new TTarget(group,starting_point,ending_point);
		
		TControl controler = new TControl(target);
		target.setControl(controler);
		
		myLevel.addTarget(target);		
		targets_alive++;
		
	}	
	
	boolean check_spawn(){
		if(spawn_rule==AT_A_TIME && (targets_alive < spawn_timing ))return true;	
		return false;
	}
	
	boolean check_destination(TTarget target){
		//Checks if target has reached destination
		//This is not reliable and requires a massive error margin (I think that's to do with how i calculate atan)
		//Correction - it's broken!		
		float position[] = new float[3];
		target.group.getTranslation(position);
		int x=(int)position[0];
		int z=(int)position[2];

/*
		System.out.println("destination X "+target.destination.X + " Z: "+target.destination.Z);		
		System.out.println("rangeX >"+(target.destination.X-distance_error) + " and X < "+(target.destination.X+distance_error));
		System.out.println("rangeZ >"+(target.destination.Z-distance_error) + " and Z < "+(target.destination.Z+distance_error));
		System.out.println("x:"+x+" z:"+z);
*/
		//if(x==target.destination.X && z==target.destination.Z)return true;		

		if(x > target.destination.X-distance_error && x < target.destination.X+distance_error && z > target.destination.Z-distance_error && z < target.destination.Z+distance_error)return true;
		//if(z > target.destination.Z - distance_error && z < target.destination.Z+distance_error)return true;		
		return false;
	}
	public void informShot(TTarget t){}
	public void informDestroyed(TTarget t){
		targets_alive--;	
	}


}