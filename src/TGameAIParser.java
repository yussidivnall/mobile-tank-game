import java.io.*;
//Loads the AI Configuration

public class TGameAIParser{
	final String AI_RULES="ai_rules:";
	final String AI_SPAWN_TIMING="ai_spawn_timing:";
	final String AI_SPAWN_LOCATION="ai_spawn_location:";
	final String AI_TARGET_DESTINATION="ai_target_destination:";
	
	TGameAI myAI;
	TGameAIParser(String ai_file,TGameAI ai){
		try{
			myAI = ai;		
		
			InputStream is = this.getClass().getResourceAsStream(ai_file);
			String l ="";
			while((l=TParser.readLine(is))!=null){
				parse(l);			
			}			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
	
	}
	
	public void parse(String args){
			if(args.startsWith(AI_RULES))parse_rules(args.substring(AI_RULES.length()));
			if(args.startsWith(AI_SPAWN_TIMING))parse_spawnTiming(args.substring(AI_SPAWN_TIMING.length()));
			if(args.startsWith(AI_SPAWN_LOCATION))parse_spawnLocation(args.substring(AI_SPAWN_LOCATION.length()));
			if(args.startsWith(AI_TARGET_DESTINATION))parse_targetDestination(args.substring(AI_TARGET_DESTINATION.length()));
	}
	public void parse_rules(String s){
		System.out.println("Rules: "+s);
		if(s.equals("stop_that_truck"))myAI.setRuleset(myAI.STOP_THAT_TRUCK);
	}	
	public void parse_spawnTiming(String s){
		if(TParser.sub(s,',',0).equals("at_a_time")){
			System.out.println("Spawnrule: at_a_time");
			int n = Integer.parseInt(TParser.sub(s,',',1));
			myAI.setSpawnRule(myAI.AT_A_TIME,n);		
		}	
	}
	public void parse_spawnLocation(String s){
		if(TParser.sub(s,',',0).equals("random_source")){
			System.out.println("random source");
			myAI.setSpawnLocation(myAI.RANDOM_SOURCE);		
		}
	}
	public void parse_targetDestination(String s){
		if(TParser.sub(s,',',0).equals("random_destination")){
			System.out.println("random destination");
			myAI.setTargetDestination(myAI.RANDOM_DESTINATION);		
		}			
	}
	

}