import javax.microedition.m3g.*;
import java.io.*;
import java.util.Hashtable;
import java.util.*;
import javax.microedition.lcdui.*;
public class Level{
	final static int _RULE_STOP_THAT_TRUCK=1;

	float MinX,MaxX,MinZ,MaxZ;

	int score = 0;
	int rule_set;
	String Level_Name;
	
	
	
	World myWorld;	
	String GAME_OBJECTS ="/GameObjects.m3g"; // Contains in the order:Light(0),Camera(1),Tank(2)
	Camera camera;	
	Group playersGroup;
	Object3D playersObject;
	Sprite3D explosionSprite;
	
	Group groundGroup;
		
	Background myBackground;
	
	Hashtable trucks; // stores only the Group
	Hashtable sources; // stores TBuilding
	Hashtable destinations;	// stores TBuilding
	Hashtable idles; // stores TIdleSprite3D - objects that do nothing, trees... (Doesn't have to be sprites); 	
	
	
	Hashtable targets; // stores TTarget
	int lastTarget=0;	
	
	TGameAI myAI;
	
	Level(World w){
		trucks = new Hashtable();	
		sources=new Hashtable(); // index,TBuilding
		destinations=new Hashtable();
		idles = new Hashtable();		
		targets = new Hashtable();		
		
		myAI = new TGameAI(this);		
		
		myWorld = w;
		try{
			init_world();
		}catch(Exception e){e.printStackTrace();}
	}
	public void init_world() throws IOException{
		playersGroup = new Group();
		      //Load once
            Object3D gameObjs[] = Loader.load(GAME_OBJECTS);
            Group tempGameObjGrp = (Group)gameObjs[0];
            Light light = (Light)tempGameObjGrp.getChild(0);
            camera =(Camera)tempGameObjGrp.getChild(1);
            playersObject = tempGameObjGrp.getChild(2);// Should be in a seperate file
            tempGameObjGrp.removeChild(light);
            tempGameObjGrp.removeChild(camera);
            tempGameObjGrp.removeChild((Node)playersObject);
            playersGroup.addChild((Node)playersObject);
            playersGroup.addChild(camera);
            
            myWorld = (World)tempGameObjGrp; // This is instead of setting the world up manually (sort of a cheat)
            myWorld.addChild((Node)light);
            myWorld.setActiveCamera(camera);
            myWorld.addChild((Node)playersGroup);     
            
		/*	VERY TEMPORARY!!!!!
		*/		
				//Explosion
		      Image explosionImg = Image.createImage("/explosion2.gif");
            Image2D explosionImg2D = new Image2D(Image2D.RGBA,explosionImg);
	
	   		CompositingMode cm = new CompositingMode();
				cm.setBlending(CompositingMode.ALPHA);      
            
            Appearance app = new Appearance();
            //app.setLayer(9);
            app.setCompositingMode(cm);
            
            explosionSprite = new Sprite3D(true,explosionImg2D,app);
            explosionSprite.setTranslation(0, 0, 0);            
            
            /*
				Fog f = new Fog();
				f.setColor(0xFF0000);
				f.setDensity(0.9f);
				f.setLinear(1f,5f);
				Appearance wa = new Appearance();
				wa.setFog(f);
				*/				
				//myWorld.setAppearance(wa);            
              
	}
	//Do all the parsing from here, maybe needs to be in a different class for the sake of cleanliness
	public void load(String level){
		try{		
		LevelParser parser = new LevelParser(level,this);
		myWorld.addChild(groundGroup);
		myWorld.setBackground(myBackground);
		loadSources();
		loadDestinations();
		loadIdles();
		
		}catch(Exception e){e.printStackTrace();}            
	}
	
	public void loadSources(){
			System.out.println("Number of sources:"+sources.size());
			for(Enumeration e=sources.elements();e.hasMoreElements();){
				TBuilding build=(TBuilding)e.nextElement();
				putBuilding(build);			
			}
	}
	public void loadDestinations(){
				
			for(Enumeration e=destinations.elements();e.hasMoreElements();){
				TBuilding build=(TBuilding)e.nextElement();
				putBuilding(build);			
			}	
						
	}
	public void loadIdles(){
				
			for(Enumeration e=idles.elements();e.hasMoreElements();){
				TIdleSprite3D sprite=(TIdleSprite3D)e.nextElement();
				myWorld.addChild(sprite.group);
				sprite.group.setTranslation(sprite.translation.X,sprite.translation.Y,sprite.translation.Z);
				sprite.group.scale(sprite.scale.X,sprite.scale.Y,sprite.scale.Z);				
				//putBuilding(sprite);			
			}	
						
	}
	
	
	
	public void putBuilding(TBuilding build){
		myWorld.addChild(build.group);
		build.group.setTranslation(build.translation.X,build.translation.Y,build.translation.Z);
		//build.group.setOrientation(1,build.rotation.X,build.rotation.Y,build.rotation.Z);
	}

	public void setBoundaries(float min_x,float max_x,float min_z,float max_z){
		MinX=min_x;MaxX=max_x;MinZ=min_z;MaxZ=max_z;	
	}

	public void setGround(Group g){
		groundGroup=g;
	}
	public void setBackground(Background bg){
		myBackground=bg;
	}	
	
	public void addTruckModel(int num,Group mod){
			trucks.put(new Integer(num),mod);	
	}
	public void addSource(int num,TBuilding build){
		sources.put(new Integer(num),build);
	}
	public void addDestination(int num,TBuilding build){
		destinations.put(new Integer(num),build);	
	}
	public void addIdleSprite(int num,TIdleSprite3D sprite){
		idles.put(new Integer(num),sprite);
	}	



	public void addTarget(TTarget target){
		myWorld.addChild(target.group);
		//target.group.setTranslation(target.source.X,target.source.Y,target.source.Z);	
		targets.put(new Integer(lastTarget),target);
		lastTarget++;	
	}	
	
	
	public void SomethingShot(Node n){

		for (Enumeration e=targets.elements();e.hasMoreElements();){
			TTarget t=(TTarget)e.nextElement();
			if(n.getParent()==t.group){
				t.shot();
				myAI.informShot(t);
				if(t.hit_points <=0){
					score+=t.value;	
					myWorld.removeChild(t.group);
					targets.remove(e);
					myAI.informDestroyed(t);
				}
			}
		}
	}
	public void advance(float Time){
		myAI.advance(Time);
		/*		
		for(Enumeration e=targets.elements();e.hasMoreElements();){
			TTarget t = (TTarget)e.nextElement();
			t.advance();		
		}
		*/	
		
		//Do animations	
	}	
	
}