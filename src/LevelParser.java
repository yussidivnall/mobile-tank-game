import javax.microedition.m3g.*;
import javax.microedition.lcdui.*;
import java.io.*;
import java.util.Hashtable;
public class LevelParser{
	final String LEVEL_NAME="level_name:";
	final String LEVEL_INTRO="level_intro:"; // deal with you later!
	final String LEVEL_RULES="level_rules:";
	final String LEVEL_NUMBER_OF_SOURCES="level_number_of_sources:";
	final String LEVEL_NUMBER_OF_DESTINATIONS="level_number_of_destinations:";
	final String LEVEL_TPM = "level_trucks_per_minute:";
	final String SOURCE_MODEL = "level_source_model:";
	final String DESTINATION_MODEL = "level_destination_model:";
	final String TRUCK_MODEL = "level_truck_model:";
	final String GROUND = "level_ground:";
	final String BACKGROUND  = "level_background:";
	final String IDLE_SPRITE3D = "level_idle_sprite3d:";
	final String LEVEL_AI = "level_ai:";	
	
	Hashtable models; // To check against before loading a model twice!
	
	Level myLevel;

	LevelParser(String level_file,Level l){
		models = new Hashtable();		
		myLevel=l;
		try{
			InputStream is = this.getClass().getResourceAsStream(level_file);
			int r=0;
			String o="";
			while((r=is.read())!=-1){			
				o+=(char)r;
				if((char)r=='\n'){parse(o);o="";}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}	

	public void parse(String arg){
		//System.out.println("Parsing:"+arg);
		try{
			//if(arg.startsWith(TAG)) parse_tagFunction(arg.substring(TAG.length()));
			if(arg.startsWith(LEVEL_NAME)) parse_levelName(arg.substring(LEVEL_NAME.length()));
			if(arg.startsWith(LEVEL_RULES)) parse_levelRules(arg.substring(LEVEL_RULES.length()));	
			if(arg.startsWith(TRUCK_MODEL)) parse_levelTruckModel(arg.substring(TRUCK_MODEL.length()));
			if(arg.startsWith(GROUND)) parse_levelGround(arg.substring(GROUND.length()));
			if(arg.startsWith(BACKGROUND)) parse_levelBackground(arg.substring(BACKGROUND.length()));
			if(arg.startsWith(SOURCE_MODEL)) parse_sourceModel(arg.substring(SOURCE_MODEL.length()));
			if(arg.startsWith(DESTINATION_MODEL)) parse_destinationModel(arg.substring(DESTINATION_MODEL.length()));
			if(arg.startsWith(IDLE_SPRITE3D)) parse_idleSprite3D(arg.substring(IDLE_SPRITE3D.length()));
			if(arg.startsWith(LEVEL_AI)) parse_AI(arg.substring(LEVEL_AI.length()));
			//IDLE_SPRITE3D LEVEL_AI
		}catch(IOException e){
			e.printStackTrace();		
		}
	}	
	public void parse_AI(String s){
		myLevel.myAI.load(s.trim());	
	}	
	
	public void parse_levelName(String s){
		myLevel.Level_Name = s;

	}
	
	public void parse_levelRules(String s){
		if(s.startsWith("Stop_that_truck")){
			myLevel.rule_set = Level._RULE_STOP_THAT_TRUCK;		
		}
	}
	public void parse_idleSprite3D(String s){
		int sprite_num = Integer.parseInt(sub(s,',',0));
		String sprite_file=sub(s,',',1);
		String trans=sub(s,',',2);
		String scale_factor = sub(s,',',3);
		Group group = loadSprite(sprite_file);
		TPoint point = getPoint(trans);
		TPoint scale = getPoint(scale_factor);
		TIdleSprite3D sprite = new TIdleSprite3D((Group)group.duplicate(),point,scale);
		myLevel.addIdleSprite(sprite_num,sprite);
		System.out.println("Adding sprite3D!");
	}	
	
	
	public void parse_levelTruckModel(String s){
		String truck_model_number="";		
		String model_file="";		
		
		truck_model_number=sub(s,',',0);		
		model_file=sub(s,',',1);		
		int num = (int)Integer.parseInt(truck_model_number);		
		Group truck = loadModel(model_file);
		if(truck==null)System.out.println("Should not of gotton NULL!!!! VERY BAD!!!!");
				
		myLevel.addTruckModel(num,truck);		
	}
	public void parse_levelGround(String s){
		Group ground = loadModel(s.trim());
		myLevel.setGround(ground);
	}
	public void parse_levelBackground(String s) throws IOException{
		Background bg = new Background();
      bg.setImage(new Image2D(Image2D.RGB,Image.createImage(s.trim())));
		myLevel.setBackground(bg);	
	}
	public void parse_sourceModel(String s){
		int src_num = Integer.parseInt(sub(s,',',0));
		TBuilding src_building=getBuilding(s);	
		myLevel.addSource(src_num,src_building);
	}
	public void parse_destinationModel(String s){
		int dest_num = Integer.parseInt(sub(s,',',0));
		TBuilding dest_building=getBuilding(s);
		myLevel.addDestination(dest_num,dest_building);
	}
	public TBuilding getBuilding(String s){
		//Gets the arguments for source/destination and returns a TBuilding
		String building_file="";
		String trans="";
		String rot="";	
		String tx,ty,tz ="";
		String rx,ry,rz ="";		
		building_file=sub(s,',',1);
		trans=sub(s,',',2);
		rot=sub(s,',',3);
		tx=sub(trans,':',0);	ty=sub(trans,':',1);tz=sub(trans,':',2);
		rx=sub(rot,':',0);ry=sub(rot,':',1);rz=sub(rot,':',2);
		TPoint translation=new TPoint(Integer.parseInt(tx),Integer.parseInt(ty),Integer.parseInt(tz));
		TPoint rotation=new TPoint(Integer.parseInt(rx),Integer.parseInt(ry),Integer.parseInt(rz));
		Group group=loadModel(building_file);
		TBuilding building = new TBuilding((Group)group.duplicate(),translation,rotation);
		return building;
	}
	
	
	
	public TPoint getPoint(String s){
		int x = Integer.parseInt(sub(s,':',0));
		int y = Integer.parseInt(sub(s,':',1));
		int z = Integer.parseInt(sub(s,':',2));
		return new TPoint(x,y,z);
			
	}	
	
	public String sub(String l,char d,int n){
		String line = l;char del = d;int num=n;
		//int start = 0;
		int currentArg=0;
		String ret="";
		for(int c=0;c<line.length();c++){
			if(line.charAt(c)==del)currentArg++;
			if(currentArg==num && line.charAt(c)!=del)ret+=line.charAt(c);
			if(currentArg>num)break;
		}
		return ret.trim();
	}	
	public Group loadSprite(String img_file){
		try{
			if(models.get(img_file)==null){
				Image img = Image.createImage(img_file);
				Image2D img2D = new Image2D(Image2D.RGBA,img);
	   		CompositingMode cm = new CompositingMode();
				cm.setBlending(CompositingMode.ALPHA);
            Appearance app = new Appearance();
            app.setCompositingMode(cm);
            Sprite3D sprt3D = new Sprite3D(true,img2D,app);
            Group retGroup = new Group();
            retGroup.addChild(sprt3D);
				models.put(img_file,retGroup);
				return retGroup;
			}else{
				//from cache
				return (Group)models.get(img_file);
			}
		}catch(Exception e){e.printStackTrace();}
		return null;	
	}


	
	public Group loadModel(String mod_file){
		try{
			if(models.get(mod_file)==null){
					Object3D Objs[] = Loader.load(mod_file);            
            	Group tempGroup = (Group)Objs[0];
					Object3D Obj = (Object3D)tempGroup.getChild(0);
					tempGroup.removeChild((Node)Obj);
					Group retGroup = new Group();				
					retGroup.addChild((Node)Obj);
					//Add to cache
					models.put(mod_file,retGroup);
					return retGroup;
			}else{
				System.out.println("Found model in cache");
				return (Group)models.get(mod_file);
			}
		}catch(IOException e){e.printStackTrace();}
		return null;
	}


}