import javax.microedition.m3g.*;
import java.io.*;
import java.util.Hashtable;
import java.util.*;
import javax.microedition.lcdui.*;
public class Level{
	final static int _RULE_STOP_THAT_TRUCK=1;


	int rule_set;
	String Level_Name;
	
	
	
	World myWorld;	
	String GAME_OBJECTS ="/GameObjects.m3g"; // Contains in the order:Light(0),Camera(1),Tank(2)
	Camera camera;	
	Group playersGroup;
	Object3D playersObject;
	Sprite3D explosionSprite;
	
	Group groundGroup;
	Hashtable trucks;	
	Background myBackground;
	Hashtable sources;
	Hashtable destinations;	
	
	Level(World w){
		trucks = new Hashtable();	
		sources=new Hashtable(); // index,TBuilding
		destinations=new Hashtable();
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
		      Image explosionImg = Image.createImage("/explosion1.gif");
            Image2D explosionImg2D = new Image2D(Image2D.RGBA,explosionImg);
	
	   		CompositingMode cm = new CompositingMode();
				cm.setBlending(CompositingMode.ALPHA);      
            
            Appearance app = new Appearance();
            //app.setLayer(9);
            app.setCompositingMode(cm);
            
            explosionSprite = new Sprite3D(true,explosionImg2D,app);
            explosionSprite.setTranslation(0, 0, 0);            
              
	}
	//Do all the parsing from here, maybe needs to be in a different class for the sake of cleanliness
	public void load(String level){
		try{		
		LevelParser parser = new LevelParser(level,this);
		myWorld.addChild(groundGroup);
		myWorld.setBackground(myBackground);
		loadSources();
		loadDestinations();

		
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
	public void putBuilding(TBuilding build){
		myWorld.addChild(build.group);
		build.group.setTranslation(build.translation.X,build.translation.Y,build.translation.Z);
		//build.group.setOrientation(1,build.rotation.X,build.rotation.Y,build.rotation.Z);
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
}